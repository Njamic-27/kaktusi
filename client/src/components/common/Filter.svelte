<script>
  import { createEventDispatcher, onMount } from "svelte";
  import { fade, slide } from "svelte/transition";
  import CategoryGrid from "./CategoryGridView.svelte";

  let categories;
  let loaded = false;
  export let selected;
  let address;

  let dispatch = createEventDispatcher();

  onMount(async () => {
    categories = [
      { value: "ELECTRIC", icon: "fa-solid fa-bolt" },
      { value: "HANDICAP", icon: "fa-solid fa-wheelchair" },
      { value: "NORMAL", icon: "fa-solid fa-square-parking" },
    ];
    loaded = true;
  });

  const applyFilter = () => {
    dispatch("selectCategories", { selected, address });
  };
</script>

<main>
  <div class="container" in:slide out:slide>
    <div class="category-container">
      <div class="title">Choose categories</div>
      {#if loaded}
        <CategoryGrid {categories} bind:selected />
      {/if}
      <input
        class="address"
        type="text"
        placeholder="Enter address"
        bind:value={address}
      />
      <button on:click={applyFilter} in:fade={{ delay: 100 }}>Confirm</button>
    </div>
  </div>
</main>

<style>
  .container {
    position: fixed;
    height: 100vh;
    width: 100%;
    background-color: var(--color-accent);
    left: 0;
    top: 0;
    z-index: 3;
  }

  .address {
    border: 5px solid var(--color-primary);
    border-radius: 15px;
    width: 70%;
    padding: 10px;
    font-family: "Poppins";
    font-size: large;
  }

  .category-container {
    margin-top: 8vh;
    height: 84vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .title {
    margin-top: 30px;
    font-size: larger;
    color: white;
  }

  button {
    background: var(--color-primary);
    border: 3px solid var(--color-accent);
    border-radius: 15px;
    color: white;
    padding: 5px;
    font-size: larger;
    font-weight: 800;
    width: 30%;
    margin-top: 10px;
  }
</style>
