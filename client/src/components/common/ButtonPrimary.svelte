<script>
  import { createEventDispatcher } from "svelte";

  let dispatch = createEventDispatcher();

  const handleClick = () => {
    dispatch("click");
  };
  export let text;
</script>

<button on:click={handleClick}>
  {text}
</button>

<style>
  button {
    background-color: var(--color-primary);
    color: white;
    border: none;
    border-radius: 15px;
    padding: 12px 40px;
    font-size: 1.4rem;
    transition: transform 0.2s ease;
  }
</style>
