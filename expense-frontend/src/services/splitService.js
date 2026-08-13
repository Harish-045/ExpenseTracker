import api from "../api/axios";

export const createSplit = (data) => {
    return api.post("/splits", data);
};

export const getMySplits = () => {
    return api.get("/splits");
};