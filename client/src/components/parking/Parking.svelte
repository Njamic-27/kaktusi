<script>
  import { SEE_OTHER } from "http-status-codes";
  import ParkingMap from "./ParkingMap.svelte";
  let selectedSpot = null;
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
    selectedSpot = data;
    if(selectedSpot){
      
    }
    console.log(data);
  }
</script>

<main>
  <ParkingMap {spots} on:parkingSelect={handleParkingSelect} />
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
