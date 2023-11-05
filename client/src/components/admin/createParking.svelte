<script>
  import { parkingApi } from "@/api";

  let longitude = "";
  let latitude = "";
  let parkingSpotZone = "Zone1"; // Set the default value to Zone1

  const parkingZones = ["Zone1", "Zone2", "Zone3", "Zone4"];

  async function handleSubmit() {
    // Handle form submission here
    await parkingApi.insert(longitude, latitude, parkingSpotZone);
  }
</script>

<main>
  <form on:submit={handleSubmit}>
    <label for="longitude">Longitude:</label>
    <input type="text" id="longitude" bind:value={longitude} required />

    <label for="latitude">Latitude:</label>
    <input type="text" id="latitude" bind:value={latitude} required />

    <label for="parkingSpotZone">Parking Spot Zone:</label>
    <select id="parkingSpotZone" bind:value={parkingSpotZone}>
      {#each parkingZones as zone (zone)}
        <option value={zone}>{zone}</option>
      {/each}
    </select>

    <button type="submit">Submit</button>
  </form>
</main>

<style>
  main {
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    top: 20%;
    right: 30%;
  }
  form {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }
</style>
