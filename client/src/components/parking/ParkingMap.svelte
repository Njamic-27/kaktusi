<script>
    import { onMount } from "svelte";
    import L from "leaflet";
    import "leaflet/dist/leaflet.css";
  
    const latitude = 45.815; // Zagreb's latitude
    const longitude = 15.9819; // Zagreb's longitude
    const zoomLevel = 12;
  
    onMount(() => {
      const map = L.map("map").setView([latitude, longitude], zoomLevel);
  
      L.tileLayer("https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Tiles style by <a href="https://www.hotosm.org/" target="_blank">Humanitarian OpenStreetMap Team</a> hosted by <a href="https://openstreetmap.fr/" target="_blank">OpenStreetMap France</a>',
      }).addTo(map);
  
      const spot1 = L.marker([45.8211, 15.97]).addTo(map);
      spot1.bindPopup("Spot 1");
      const spot2 = L.marker([45.8212, 15.97]).addTo(map);
      spot2.bindPopup("Spot 22");
      const spot3 = L.marker([45.823, 15.97]).addTo(map);
      spot3.bindPopup("Spot 3");
      const spot4 = L.marker([45.824, 15.97]).addTo(map);
      spot4.bindPopup("Spot 4");
    });
  </script>
  
  <div id="map" />
  
  <style>
    #map {
      height: 100%; /* Adjust the height as needed for mobile display */
      width: 100%;
    }
  </style>
  