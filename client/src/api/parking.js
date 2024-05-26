import { extractData } from "./helpers";
import request from "./request";

const urls = {
  root: "/parking",
  get fetchAll() {
    return this.root + "/all";
  },
  fetchPrice(id) {
    return `${this.root}/price/${id}`;
  },
  update(id) {
    return `${this.root}/update/${id}`;
  },
  deleteSpot(id) {
    return `${this.root}/delete/${id}`;
  },
  get insert() {
    return this.root + "/add";
  },
};

const fetchAll = async () => {
  const result = await request.get(urls.fetchAll).then(extractData);
  return result;
};

const insert = async (longitude, latitude, zone, parkingSpotType) => {
  const result = await request
    .post(urls.insert, { longitude, latitude, zone, parkingSpotType })
    .then(extractData);
  return result;
};

const fetchPrice = (id) => {
  const result = request.get(urls.fetchPrice(id)).then(extractData);
  return result;
};

const update = async (id, zone, type) => {
  const result = await request
    .put(urls.update(id), { zone, type })
    .then(extractData);
  return result;
};
const deleteSpot = async (id) => {
  console.log("deleting");
  const result = await request.delete(urls.deleteSpot(id));
  return result;
};

export default {
  fetchAll,
  fetchPrice,
  update,
  deleteSpot,
  insert,
};
