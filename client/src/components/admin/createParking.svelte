<script>
  import { parkingApi } from "@/api";

  let longitude = "";
  let latitude = "";
  let parkingSpotZone = "Zone1"; // Set the default value to Zone1
  let parkingSpotType = "normal"

  const parkingZones = ["Zone1", "Zone2", "Zone3", "Zone4"];
  const parkingTypes = ["normal", "electric", "handicap"];


  async function handleSubmit() {
    await parkingApi.insert(longitude, latitude, parkingSpotZone, parkingSpotType);
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

    <label for="parkingSpotType">Parking Spot Type:</label>
    <select id="parkingSpotType" bind:value={parkingSpotType}>
      {#each parkingTypes as type (type)}
        <option value={type}>{type}</option>
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
    width: 100%;
    height: 100%;
  }
  form {
    width: 90%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }

  input {
    margin-top: 5px;
    width: 90%;
    height: 50px;
    border: none;
    background-color: var(--color-primary);
    border-radius: 10px;
    color: white;
    font-size: large;
  }

  select {
    width: 50%;
    height: 50px;
    border: none;
    background-color: var(--color-primary);
    color: white;
    border-radius: 8px;
    font-size: large;
  }

  button {
    margin: 20px;
    width: 40%;
    background-color: var(--color-primary);
    color: white;
    height: 40px;
    border: none;
    border-radius: 8px;
  }
</style>
