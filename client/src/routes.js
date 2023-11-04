import {
  isAdminRoute,
  isAuthRoute,
  isRoute,
  redirect,
} from "./utils/router/routing";
import Auth from "./components/auth/Auth.svelte";
import Home from "./components/home/Home.svelte";
import Parking from "@/components/parking/Parking.svelte";
import { isAdmin, isLoggedIn } from "./stores/auth";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/auth",
    name: "Auth",
    component: Auth,
  },
  {
    path: "/map",
    name: "Parking",
    component: Parking,
  },
];

const beforeRoute = () => {
  if (!isRoute()) {
    return redirect("Home");
  }

  if (!isLoggedIn() && !isAuthRoute()) {
    return redirect("Auth");
  }

  if (isLoggedIn() && isAuthRoute()) {
    return redirect("Home");
  }

  if (isAdminRoute() && !isAdmin()) {
    return redirect("Home");
  }
};

export default routes;
export { beforeRoute };
