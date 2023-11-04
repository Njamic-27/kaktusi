<script>
  import { fade, fly, scale, slide } from "svelte/transition";
  import { onMount } from "svelte";

  export let spot;
  let address = "";
  let displayCard = false;

  // Latitude and Longitude of the location you want to reverse geocode
  const lat = spot.latitude; // Example latitude
  const lng = spot.longitude; // Example longitude

  // Create a Nominatim API request URL
  const nominatimUrl = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;

  onMount(() => {
    // Make a request to the Nominatim API
    fetch(nominatimUrl)
      .then((response) => response.json())
      .then((data) => {
        address = `${data.address.road} ${
          data.address.house_number ? data.address.house_number : "BB"
        }`;
        displayCard = true;
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  });
</script>

<main>
  {#if displayCard}
    <div class="cardContainer" in:slide={{ delay: 200 }} out:slide />
  {/if}
</main>

<style>
  main {
    position: absolute;
    z-index: 1;
    bottom: 0;
    width: 100%;
    height: 35%;
    display: flex;
    justify-content: center;
    align-items: flex-end;
  }

  .cardContainer {
    position: relative;
    z-index: 1;
    height: 300px; /* Set the desired fixed height */
    width: 100%;
    background-color: var(--color-white);
    border-top: 5px solid var(--color-primary);
    border-left: 2px solid var(--color-primary);
    border-right: 2px solid var(--color-primary);
    border-top-right-radius: 20px;
    border-top-left-radius: 20px;
    display: flex;
    flex-direction: column;
    overflow-y: auto; /* Enable both mouse and touch scrolling */
  }
</style>
