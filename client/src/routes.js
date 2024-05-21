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
import CreateParking from "./components/admin/createParking.svelte";
import Account from "./components/account/Account.svelte";

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
  {
    path: "/account",
    name: "Account",
    component: Account,
  },
  {
    path: "/admin",
    name: "CreateSpot",
    component: CreateParking,
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
