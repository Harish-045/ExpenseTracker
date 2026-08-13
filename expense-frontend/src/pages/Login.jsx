import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { loginUser } from "../services/authService";
import { AuthContext } from "../context/AuthContext";
import "../styles/auth.css";
import { toast } from "react-toastify";

function Login() {

    const navigate = useNavigate();

    const { login } = useContext(AuthContext);

    const [formData, setFormData] = useState({

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

            const response = await loginUser(formData);

            login(response.data.token);

            navigate("/dashboard");

        }

        catch (error) {

            toast.error("Invalid Email or Password");

        }

    };

    return (

        <div className="auth-container">

            <form
                className="auth-card"
                onSubmit={handleSubmit}
            >

                <h2>ExpenseWise</h2>

                <p>Login to your account</p>

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

                    Login

                </button>
                 <div style={{ marginTop: "10px" }}>
                     <Link to="/forgot-password">
                        Forgot Password?
                     </Link>
                 </div>

                <span>

                    Don't have an account?

                    <Link to="/register">

                        Register

                    </Link>

                </span>

            </form>

        </div>

    );

}

export default Login;
