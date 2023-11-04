import { extractData } from './helpers';
import request from './request';

const urls = {
  root: '/accomodations',
  get fetchPublished() {
    return this.root + '/all';
  },
  get fetchByUser() {
    return this.root + '/user';
  },
  remove(id) {
    return `${this.root}/${id}`;
  },
  updateData(data) {
    return `${this.root}/update-data/${data.id}`;
  },
  get create() {
    return this.root;
  },
  get insertBooking() {
    return `${this.root}/insert-booking`;
  },
  fetchById(id) {
    return `${this.root}/${id}`;
  },
};

const fetchPublished = () => {
  return request.get(urls.fetchPublished).then(extractData);
};

const fetchByUser = () => {
  return request.get(urls.fetchByUser).then(extractData);
};

const updateData = data => {
  return request.post(urls.updateData(data.id), { data });
};

const remove = id => {
  return request.delete(urls.remove(id));
};

const create = (accomodation, category, pictures) => {
  return request.post(urls.create, { accomodation, category, pictures }).then(extractData);
};

const fetchById = id => {
  return request.get(urls.fetchById(id)).then(extractData);
};

export default {
  fetchPublished,
  fetchByUser,
  updateData,
  remove,
  create,
  fetchById,
};
