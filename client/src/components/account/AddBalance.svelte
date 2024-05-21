<script>
  import { fade, fly, scale } from "svelte/transition";
  import { createEventDispatcher } from "svelte";

  let balance = 10;
  let dispatch = createEventDispatcher();

  function decreaseBalance() {
    if (balance - 5 >= 0) balance -= 5;
  }
  function increaseBalance() {
    balance += 5;
  }
  function reactToClickOutside() {
    dispatch("clickOutside");
  }

  function addBalance() {
    dispatch("addBalance", balance)
  }
</script>

<main class="addBalanceContainer" in:scale out:scale>
  <div class="balancePicker">
    <button class="buttonSecondary" on:click={decreaseBalance}>-</button>
    <div class="balance">{balance} €</div>
    <button class="buttonSecondary" on:click={increaseBalance}>+</button>
  </div>
  <div class="buttonContainer">
    <button class="buttonPrimary" on:click={addBalance}>Add balance</button>
  </div>
</main>
<div
  class="clickableAround"
  on:click={reactToClickOutside}
  on:keydown={reactToClickOutside}
  in:fly
  out:fly
></div>

<style>
  .clickableAround {
    position: fixed;
    background-color: rgba(0, 0, 0, 0.7);
    top: 0;
    height: 100vh;
    width: 100vw;
    z-index: 1;
  }
  main {
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    width: 90%;
    height: 30vh;
    background-color: var(--color-accent);
    border-radius: 15px;
    z-index: 2;
  }

  .balance {
    font-size: 4rem;
    font-weight: 800;
  }

  .balancePicker {
    display: flex;
    justify-content: space-around;
    align-items: center;
    height: 15vh;
    width: 100%;
  }

  .buttonSecondary {
    width: 6vh;
    height: 6vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: var(--color-primary);
    border-radius: 50%;
    color: white;
    font-weight: 400;
    font-size: 3rem;
    border: none;
    cursor: pointer;
    transition: transform 0.1s ease;
  }

  .buttonContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 50%;
  }

  .buttonPrimary {
    border: none;
    background-color: var(--color-primary);
    color: white;
    font-weight: 400;
    font-size: 1.5rem;
    padding: 15px;
    border-radius: 15px;
  }
</style>
