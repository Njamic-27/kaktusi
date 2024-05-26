<script>
  import { slide } from "svelte/transition";
  import OptionButton from "../common/OptionButton.svelte";
  import ButtonPrimary from "../common/ButtonPrimary.svelte";

  export let parkingLot;
  let showExtraOptions = false;
  let selectedHour;

  let selected = [false, false, false];

  function handleHourSelection({ detail: hour }) {
    selectedHour = hour;
    toggleClickedButton(hour);
  }

  function toggleClickedButton(value) {
    selected = [false, false, false];
    selected[value - 1] = true;
  }

  function toggleExtraOptions() {
    //provjeriti ako capacity nije full, ako nije:
    showExtraOptions = !showExtraOptions;
  }
</script>

<main>
  <button class="parkingLot" on:click={toggleExtraOptions}>
    <div class="name">Address: ParkingLot</div>
    <div class="capacity">Capacity/capacity</div>
  </button>
  {#if showExtraOptions}
    <div class="extraOptions" in:slide out:slide>
      <hr class="divider" />
      <div class="extraOptionsTitle">Book a spot for:</div>
      <div class="timeSelectGrid">
        <div class="option">
          <OptionButton
            value="1"
            selected={selected[0]}
            on:hourSelection={handleHourSelection}
          ></OptionButton>
        </div>
        <div class="option">
          <OptionButton
            value="2"
            selected={selected[1]}
            on:hourSelection={handleHourSelection}
          ></OptionButton>
        </div>
        <div class="option">
          <OptionButton
            value="3"
            selected={selected[2]}
            on:hourSelection={handleHourSelection}
          ></OptionButton>
        </div>
      </div>
      {#if selectedHour != null}
        <div class="ButtonContainer" in:slide>
          <hr class="divider" />
          <div class="price">Total price: 2.6 €</div>
          <ButtonPrimary text={"Reserve"}></ButtonPrimary>
        </div>
      {/if}
    </div>
  {/if}
</main>

<style>
  main {
    width: 90%;
    background-color: white;
    border-radius: 8px;
    margin-bottom: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }

  .parkingLot {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    border: none;
    border-radius: 10px;
    background-color: white;
    font-size: larger;
  }

  .timeSelectGrid {
    position: relative;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;
    width: 100%;
    margin-top: 10px;
    margin-bottom: 10px;
    z-index: 2;
  }

  .option {
    width: 100%;
    text-align: center;
  }

  .ButtonContainer {
    width: 100%;
    text-align: center;
    margin-bottom: 20px;
  }

  .extraOptionsTitle {
    width: 100%;
    text-align: center;
    font-size: 1.3rem;
  }
</style>
