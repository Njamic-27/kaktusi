<script>
  import ReservationCard from "./ReservationCard.svelte";
  import { createEventDispatcher } from "svelte";
  export let reservations = []; // Ensure you have a default value
  let dispatch = createEventDispatcher();

  function handleReservationExtension({ detail: data }) {
    dispatch("reservationExtension", data);
  }
</script>

<main>
  {#each reservations as reservation}
    <ReservationCard
      {reservation}
      on:reservationExtension={handleReservationExtension}
    />
  {/each}
</main>

<style>
  main {
    width: 95%;
    display: flex;
    flex-direction: column;
    gap: 10px;
    justify-content: center; /* Center the cards */
  }
</style>
