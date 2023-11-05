<script>
  import { createEventDispatcher, onMount } from "svelte";
  import L from "leaflet";
  import "leaflet/dist/leaflet.css";

  export let spots;
  export let currentState;

  let mapState = {
    latitude: 45.815,
    longitude: 15.9819,
    zoomLevel: 13,
  };

  // Define marker icons with different colors
  const redIcon = L.icon({
    iconUrl: "./src/public/markers/red_marker.png",
    iconSize: [30, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: "./node_modules/leaflet/dist/images/marker-shadow.png",
    shadowSize: [41, 41],
  });

  const greenIcon = L.icon({
    iconUrl: "./src/public/markers/green_marker.png",
    iconSize: [30, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: "./node_modules/leaflet/dist/images/marker-shadow.png",
    shadowSize: [41, 41],
  });

  const blueIcon = L.icon({
    iconUrl: "./src/public/markers/blue_marker.png",
    iconSize: [30, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: "./node_modules/leaflet/dist/images/marker-shadow.png",
    shadowSize: [41, 41],
  });

  const dispatch = createEventDispatcher();
  let selectedMarker = null;
  let minZoomToShowSpots = 16; // Adjust this zoom level as needed

  let latitude = 45.815;
  let longitude = 15.9819;
  let zoomLevel = 13;

  if (currentState) {
    latitude = currentState.latitude;
    longitude = currentState.longitude;
    zoomLevel = currentState.zoomLevel;
  }
  let map;

  function updateSpotVisibility(map) {
    const currentZoom = map.getZoom();
    spots.forEach((spot) => {
      if (currentZoom >= minZoomToShowSpots) {
        // Show the spot marker
        spot.markerData.addTo(map);
      } else {
        // Hide the spot marker
        map.removeLayer(spot.markerData);
      }
    });
  }

  onMount(async () => {
    map = L.map("map").setView([latitude, longitude], zoomLevel);

    L.tileLayer("https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png", {
      maxZoom: 19,
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Tiles style by <a href="https://www.hotosm.org/" target="_blank">Humanitarian OpenStreetMap Team</a> hosted by <a href="https://openstreetmap.fr/" target="_blank">OpenStreetMap France</a>',
    }).addTo(map);

    spots.forEach((spot) => {
      let customIcon = spot.occupied ? redIcon : greenIcon;
      spot.markerData = L.marker([spot.latitude, spot.longitude], {
        icon: spot.occupied ? redIcon : greenIcon,
      });

      spot.markerData.on("click", () => {
        // Change the icon to blue when clicked
        spot.markerData.setIcon(blueIcon);
        spot.markerData._icon.style.width = "40px";
        spot.markerData._icon.style.height = "51px";
        spot.markerData._icon.style.transition = "all 0.1s";

        if (selectedMarker && selectedMarker !== spot) {
          // Reset the icon of the previously selected marker to corresponding color
          customIcon = selectedMarker.occupied ? redIcon : greenIcon;
          selectedMarker.markerData.setIcon(customIcon);
        }

        map.on("click", () => {
          selectedMarker.markerData.setIcon(
            selectedMarker.occupied ? redIcon : greenIcon
          );
          dispatch("parkingSelect", null);
        });

        map.setView([spot.latitude - 0.0002, spot.longitude], 20);
        selectedMarker = spot;
        dispatch("parkingSelect", spot);
      });
    });

    map.on("zoomend", () => {
      updateSpotVisibility(map);
    });

    map.on("moveend", () => {
      const center = map.getCenter();
      mapState.latitude = center.lat;
      mapState.longitude = center.lng;
      mapState.zoomLevel = map.getZoom();
      dispatch("mapMove", mapState);
    });

    // Initialize spot visibility
    updateSpotVisibility(map);
  });
</script>

<main>
  <div id="map" style="height: 100vh; width: 100vw;" />
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
</style>
