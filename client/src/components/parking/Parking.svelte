<script>
  import { map } from "leaflet";
  import Filter from "../common/Filter.svelte";
  import ParkingMap from "./ParkingMap.svelte";
  import SelectedParking from "./SelectedParking.svelte";
  import { parkingApi } from "@/api";
  import { onMount } from "svelte";
  import { add } from "ol/coordinate";
  $: selectedSpot = null;
  let spots = [];
  let loaded = false;
  let selected = [];
  let showCategorySection = false;
  let mapState = null;
  $: spotsForDisplay = [];

  onMount(async () => {
    spots = await parkingApi.fetchAll();
    spotsForDisplay = spots;
    if (spotsForDisplay.length > 0) {
      loaded = true;
    }
  });

  function handleParkingSelect({ detail: data }) {
    if (selectedSpot) {
      selectedSpot = null;
    }
    selectedSpot = data;
  }

  const refreshData = async () => {
    loaded = false;
    spots = await parkingApi.fetchAll();
    spotsForDisplay = spots;
    if (spots.length > 0) {
      loaded = true;
    }
  };

  const applyFilter = async ({ detail }) => {
    let selected = detail.selected;
    toggleFilterContainerVisibility();
    loaded = false;
    if (selected.length > 0) {
      spotsForDisplay = spots.filter((spot) =>
        selected.includes(spot.parkingSpotType)
      );
    } else {
      spotsForDisplay = spots;
    }
    if (detail.address != null) {
      let data = [];

      data = await getCoordinates(detail.address);
      mapState = {
        latitude: data.latitude,
        longitude: data.longitude,
        zoomLevel: 16,
      };
    }
    setTimeout(() => {
      loaded = true;
    }, 500);
  };

  function toggleFilterContainerVisibility() {
    showCategorySection = !showCategorySection;
  }

  function saveState(e) {
    mapState = e.detail;
  }

  async function getCoordinates(address) {
    const geocodingUrl = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
      address
    )}`;

    return fetch(geocodingUrl)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Network response was not ok: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        if (data.length > 0) {
          const result = data[0];
          const latitude = parseFloat(result.lat);
          const longitude = parseFloat(result.lon);
          let res = { latitude, longitude };
          return res;
        } else {
          console.log(`No results found for ${address}`);
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }
  // Replace 'YOUR_ADDRESS' with the address you want to geocode
</script>

<main>
  {#if !loaded}
    <div class="">Loading</div>
  {:else}
    {#if showCategorySection}
      <Filter
        on:selectCategories={applyFilter}
        on:clickOutside={toggleFilterContainerVisibility}
        bind:selected
      />
    {/if}
    <button class="button" on:click={refreshData}>Refresh</button>
    <button class="filter" on:click={toggleFilterContainerVisibility}
      >Filter</button
    >
    <ParkingMap
      spots={spotsForDisplay}
      currentState={mapState}
      on:parkingSelect={handleParkingSelect}
      on:mapMove={saveState}
    />
    {#if selectedSpot !== null}
      {#key selectedSpot}
        <SelectedParking spot={selectedSpot} on:refresh={refreshData} />
      {/key}
    {/if}
  {/if}
</main>

<style>
  main {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    z-index: 0;
    height: 85vh;
    margin-top: 7.5vh;
    width: 100vw;
  }

  .button {
    position: absolute;
    top: 5px;
    right: 5px;
    z-index: 2;
    width: 100px;
    border: 3px solid var(--color-accent);
    padding: 5px;
    border-radius: 15px;
    margin: 5px;
    font-family: "Poppins";
    font-size: large;
    background-color: var(--color-primary);
    color: white;
  }

  .filter {
    position: absolute;
    top: 5px;
    left: 5px;
    z-index: 2;
    width: 100px;

    border: 3px solid var(--color-accent);
    padding: 5px;
    border-radius: 15px;
    margin: 5px;
    font-family: "Poppins";
    font-size: large;
    background-color: var(--color-primary);
    color: white;
  }
</style>
