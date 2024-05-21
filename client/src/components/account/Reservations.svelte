<script>
    import ReservationCard from "./ReservationCard.svelte";
    export let reservations = []; // Ensure you have a default value
  </script>
  
  <main>
    {#each reservations as reservation}
      <ReservationCard {reservation} />
    {/each}
  </main>
  
  <style>
    main {
      width: 85%;
      display: flex;
      flex-direction: column;
      gap: 10px;
      justify-content: center; /* Center the cards */
    }
  </style>
  