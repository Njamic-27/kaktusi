<script>
  import { createEventDispatcher } from "svelte";
  import { authApi } from "@/api";
  import { authStore } from "@/stores";
  import { redirect } from "@/utils/router/routing";
  import { requiredValidator } from "@/utils/validation/validators";
  import AuthForm from "./AuthForm.svelte";

  const dispatch = createEventDispatcher();
  let error = false;

  let inputs = {
    username: {
      value: "",
      type: "text",
      valid: false,
      label: "Username",
      validators: [requiredValidator()],
    },
    password: {
      value: "",
      type: "password",
      valid: false,
      label: "Password",
      validators: [requiredValidator()],
    },
  };

  const swap = () => dispatch("swap");

  const submit = async () => {
    const isFormValid = inputs.username.valid && inputs.password.valid;
    if (!isFormValid) return;

    const username = inputs.username.value;
    const password = inputs.password.value;
    try {
      const user = await authApi.login(username, password);
      if (user) {
        authStore.user.set(user);
        redirect("Home");
      } else {
        error = !error;
      }
    } catch (err) {
      alert(err.response.data.message);
    }
  };
</script>

<main>
  <div class="main-title">Parktus</div>
  {#if error}
    <div class="error">Failed to log in!</div>
  {/if}
  <AuthForm
    on:submit={submit}
    on:swap={swap}
    {inputs}
    headerLabel="Login"
    buttonLabel="Login"
    callToActionLabel="Don't have an account?"
    actionlabel="Click here to register!"
  />

  <div class="image-home">
    <img
      src="https://lubricants.goma.hr/wp-content/uploads/sites/3/2023/03/zagreb-1024x288.png"
      alt="ZagrebPanorama"
    />
  </div>
</main>

<style>
  @import url("https://fonts.googleapis.com/css2?family=Dancing+Script&display=swap");

  main {
    position: relative;
    overflow-y: hidden;
  }

  .error {
    color: red;
    position: absolute;
    top: 75%;
    width: 100%;
    height: 100%;
    text-align: center;
  }

  .image-home {
    position: absolute;
    display: flex;
    bottom: -10px;
    overflow: hidden;
  }

  img {
    width: 100%;
    scale: 1.04;
  }

  .main-title {
    font-size: 3rem;
    font-family: "Poppins";
    font-weight: 800;
    width: 100%;
    text-align: center;
    position: absolute;
    top: 30px;
  }
</style>
