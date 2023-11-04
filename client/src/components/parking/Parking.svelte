<script>
  import { SEE_OTHER } from "http-status-codes";
  import ParkingMap from "./ParkingMap.svelte";
  import SelectedParking from "./SelectedParking.svelte";
  import { parkingApi } from "@/api";
  import { onMount } from "svelte";
  import { slide } from "svelte/transition";
  $: selectedSpot = null;
  $: displaySelected = false;
  let spots = [];
  let loaded = false;

  onMount(async () => {
    spots = await parkingApi.fetchAll();
    if (spots.length > 0) {
      loaded = true;
    }
  });

  function handleParkingSelect({ detail: data }) {
    if (selectedSpot) {
      selectedSpot = null;
      displaySelected = false;
    }
    selectedSpot = data;
    displaySelected = true;
  }
</script>

<main>
  {#if !loaded}
    <div class="">Loading</div>
  {:else}
    <ParkingMap {spots} on:parkingSelect={handleParkingSelect} />
    {#if selectedSpot !== null}
      {#key selectedSpot}
        <SelectedParking spot={selectedSpot} />
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
</style>
