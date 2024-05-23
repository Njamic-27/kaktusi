<script>
  import { createEventDispatcher } from "svelte";

  export let value;
  export let selected;

  let dispatch = createEventDispatcher();

  let message = value > 1 ? "hours" : "hour";

  function handleHourSelection() {
    event.stopPropagation();
    dispatch("hourSelection", value);
  }
</script>

<button on:click={handleHourSelection} class:selected>
  {value}
  {message}
</button>

<style>
  button {
    background-color: var(--color-primary);
    color: white;
    border: none;
    border-radius: 15px;
    padding: 15px;
    font-size: 1.1rem;
    width: 90%;
    transition: transform 0.2s ease; 
  }

  button.selected {
    transform: scale(1.1);
  }
</style>
