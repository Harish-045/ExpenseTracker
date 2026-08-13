import api from "../api/axios";

export const getAllBudgets = () =>
    api.get("/budgets");

export const getBudgets = () =>
    api.get("/budgets");

export const addBudget = (budget) =>
    api.post("/budgets", budget);

export const updateBudget = (id, budget) =>
    api.put(`/budgets/${id}`, budget);

export const deleteBudget = (id) =>
    api.delete(`/budgets/${id}`);

export const getBudgetStatus = () =>
    api.get("/budgets/status");
export const getBudgetById = (id) =>
    api.get(`/budgets/${id}`);
