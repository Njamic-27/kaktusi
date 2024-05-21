<script>
  import AddBalance from "./AddBalance.svelte";
  import { createEventDispatcher } from "svelte";

  let dispatch = createEventDispatcher();
  let showAddBalance = false;
  export let balance;

  function handleShowAddBalance() {
    showAddBalance = !showAddBalance;
  }

  function addBalance({ detail: balance }) {
    handleShowAddBalance();
    dispatch("addBalance", balance);
  }
</script>

<main class="wallet_main">
  {#if showAddBalance}
    <AddBalance
      on:clickOutside={handleShowAddBalance}
      on:addBalance={addBalance}
    ></AddBalance>
  {/if}
  <div class="wallet_container">
    <div class="walletBalance">{balance} €</div>
    <button class="addBalance" on:click={handleShowAddBalance}>+</button>
  </div>
</main>

<style>
  main {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    margin-top: 10vh;
    width: 100%;
    height: 20vh;
  }

  .wallet_container {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 70%;
    height: 10vh;
    font-size: 4rem;
    font-weight: 800;
  }

  .addBalance {
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

  .addBalance:active {
    transform: scale(0.9);
  }
</style>
