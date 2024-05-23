<script>
  import { fade } from "svelte/transition";

  export let categories;
  export let selected;

  function toggleCategory(id) {
    const index = selected.indexOf(id);
    if (index > -1) {
      selected.splice(index, 1);
    } else {
      selected.push(id);
    }
    categories = categories;
  }
</script>

<main>
  <hr />
  <div class="container" in:fade>
    <div class="grid-container">
      {#if categories.length > 0}
        {#each categories as category}
          <div
            class="category-container"
            on:click={() => toggleCategory(category.value)}
            on:keydown={() => toggleCategory(category.value)}
          >
            <div
              class="icon {selected.includes(category.value) ? 'selected' : ''}"
            >
              <i class={category.icon} />
            </div>
            <div class="value">{category.value}</div>
          </div>
        {/each}
      {/if}
    </div>
  </div>
</main>

<style>
 
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;

  }

  .grid-container {
    width: 95%;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;
    align-items: center;
  }

  .category-container {
    margin: 5px;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    color: white;
    cursor: pointer;
  }

  .icon {
    display: flex;
    justify-content: center;
    align-items: center;
    border: 2px solid white;
    background-color: var(--color-accent);
    border-radius: 50%;
    color: white;
    font-size: xx-large;
    padding: 5px;
    height: 50px;
    width: 50px;
    transition: background-color 0.3s ease-in-out;
    transition: border 0.1s ease-in-out;
  }

  .value {
    width: 100%;
    text-align: center;
  }

  hr {
    width: 95%;
    height: 1px;
    border: none;
    margin-top: 20px;
    margin-bottom: 20px;
    background-color: var(--color-accent);
  }

  .icon.selected {
    background-color: var(--color-primary);
    transition: background-color 0.3s ease-in-out;
    transition: border 0.1s ease-in-out;
    border: 2px solid var(--color-primary);
  }
</style>
