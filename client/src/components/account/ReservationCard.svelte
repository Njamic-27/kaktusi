<script>
  import { slide } from "svelte/transition";
  import OptionButton from "../common/OptionButton.svelte";
  import ButtonPrimary from "../common/ButtonPrimary.svelte";
  import { createEventDispatcher } from "svelte";

  export let reservation;

  let dispatch = createEventDispatcher();
  let startTime = 3;
  let address = "Gundulićeva 18";
  //let currentHour = new Date().getHours();
  //let currentMinute = new Date().getMinutes()
  let selectedHour;
  let infoText = "Expires in";
  let infoTime = "20min";
  let selected = [false, false, false];

  let showExtraOptions = false;

  function toggleExtraOptions() {
    showExtraOptions = !showExtraOptions;
    selectedHour = null;
    selected = [false, false, false];
  }

  function handleHourSelection({ detail: hour }) {
    selectedHour = hour;
    toggleClickedButton(hour);
  }

  function toggleClickedButton(value) {
    selected = [false, false, false];
    selected[value - 1] = true;
  }

  function handleReservationExtension() {
    dispatch("reservationExtension", { reservation, selectedHour });
  }
</script>

<main>
  <div
    class="reservationContainer"
    on:click={toggleExtraOptions}
    on:keydown={toggleExtraOptions}
  >
    <div class="reservationInfo">
      <span>Address: {address}</span>
      <span>{infoText}: {infoTime}</span>
    </div>
    {#if showExtraOptions}
      <div class="extraOptions" in:slide out:slide>
        <hr class="divider" />
        <div class="extraOptionsTitle">Extend your reservation</div>
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
          <div class="extendButtonContainer" in:slide>
            <hr class="divider" />
            <div class="price">Total price: 2.6 €</div>
            <ButtonPrimary text={"Extend"} on:click={handleReservationExtension}
            ></ButtonPrimary>
          </div>
        {/if}
      </div>
    {/if}
  </div>
</main>

<style>
  .reservationContainer {
    position: relative;
    background-color: var(--color-accent);
    padding: 15px;
    border-radius: 15px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    z-index: 1;
  }

  .price{
    font-size: large;
    margin-bottom: 5px;
  }
  .divider {
    background-color: var(--color-primary);
    height: 1px;
    width: 100%;
  }

  .extraOptionsTitle {
    width: 100%;
    text-align: center;
    font-size: 1.3rem;
  }

  .extendButtonContainer {
    width: 100%;
    text-align: center;
  }

  .reservationInfo {
    height: 100px;
    border-radius: 15px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    font-size: 1.2rem;
  }
  .timeSelectGrid {
    margin-top: 10px;
    position: relative;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;
    width: 100%;
    z-index: 2;
  }

  .option {
    width: 100%;
    text-align: center;
  }
</style>
