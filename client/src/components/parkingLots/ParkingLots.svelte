<script>
  import { fly } from "svelte/transition";
  import ParkingLotCard from "./ParkingLotCard.svelte";
  export let parkingLots;
</script>

<main in:fly out:fly>
  <div class="title">Parking Lots</div>

  <div class="parkingLotsContainer">
    {#each parkingLots as parkingLot}
      <ParkingLotCard {parkingLot}></ParkingLotCard>
    {/each}
  </div>
</main>

<style>
  main {
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 90%;
    height: 60%; 
    background-color: var(--color-primary);
    z-index: 2;
    overflow: hidden;
    border-radius: 15px;
    padding: 10px;
  }

  .parkingLotsContainer {
    width: 100%;
    height: 90%; 
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow-y: auto;
  }

  .title{
    font-size: xx-large;
    color: white;
  }
</style>
