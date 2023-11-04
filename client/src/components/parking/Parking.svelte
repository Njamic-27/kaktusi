<script>
  import { SEE_OTHER } from "http-status-codes";
  import ParkingMap from "./ParkingMap.svelte";
  import SelectedParking from "./SelectedParking.svelte";
  $: selectedSpot = null;
  $: displaySelected = false;
  let spots = [];
  let spot1 = {
    id: "986d1c84-7b38-45f1-b117-08dbdb851562",
    latitude: 45.80528,
    longitude: 15.96649,
    parkingSpotZone: "Zone1",
    occupied: true,
    occupiedTimestamp: "2023-11-04T12:46:06.7265298",
  };
  let spot2 = {
    id: "2b6f2fdc-f466-4065-b118-08dbdb851562",
    latitude: 45.80527,
    longitude: 15.96668,
    parkingSpotZone: "Zone1",
    occupied: true,
    occupiedTimestamp: "2023-11-04T12:46:43.7265407",
  };
  spots.push(spot1);
  spots.push(spot2);

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
  <ParkingMap {spots} on:parkingSelect={handleParkingSelect} />
  {#if selectedSpot !== null}
    {#key selectedSpot}
      <SelectedParking spot={selectedSpot} />
    {/key}
  {/if}
</main>

<style>
  main {
    position: relative;
    z-index: 0;
    height: 84vh;
    margin-top: 8vh;
    width: 100vw;
  }
</style>
