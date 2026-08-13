import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { registerUser } from "../services/authService";
import "../styles/auth.css";
import { toast } from "react-toastify";

function Register() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: ""
    });

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await registerUser(formData);

            toast.success("Registration Successful");

            navigate("/");

        } catch (error) {

            toast.error("Registration Failed");

            console.log(error);

        }

    };

    return (

        <div className="auth-container">

            <form className="auth-card" onSubmit={handleSubmit}>

                <h2>ExpenseWise</h2>

                <p>Create your account</p>

                <input
                    type="text"
                    name="name"
                    placeholder="Full Name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />

                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />

                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />

                <button type="submit">

                    Register

                </button>

                <span>

                    Already have an account?

                    <Link to="/">

                        Login

                    </Link>

                </span>

            </form>

        </div>

    );

}

export default Register;