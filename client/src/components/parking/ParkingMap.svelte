<script>
  import { createEventDispatcher, onMount } from "svelte";
  import L from "leaflet";
  import "leaflet/dist/leaflet.css";
  export let spots;

  const dispatch = createEventDispatcher();

  const latitude = 45.815; // Zagreb's latitude
  const longitude = 15.9819; // Zagreb's longitude
  const zoomLevel = 13;

  onMount(() => {
    const map = L.map("map").setView([latitude, longitude], zoomLevel);

    L.tileLayer("https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png", {
      maxZoom: 19,
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Tiles style by <a href="https://www.hotosm.org/" target="_blank">Humanitarian OpenStreetMap Team</a> hosted by <a href="https://openstreetmap.fr/" target="_blank">OpenStreetMap France</a>',
    }).addTo(map);
    spots.forEach((spot) => {
      let markerData = L.marker([spot.latitude, spot.longitude]).addTo(map);
      markerData.on("click", () => {
        dispatch("parkingSelect", spot);
      });
    });
  });
  // Hide the attribution control
</script>

<div id="map" />

<style>
  #map {
    height: 100%; /* Adjust the height as needed for mobile display */
    width: 100%;
  }
</style>
