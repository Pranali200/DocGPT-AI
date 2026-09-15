import { useState } from "react";
import { login } from "../services/authService";
import { useNavigate, Link } from "react-router-dom";

import "./auth.css";


function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);


    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");
        setLoading(true);

        try {

            const data = await login({
                email,
                password
            });

            localStorage.setItem(
                "token",
                data.token
            );

            navigate("/chat", {
                replace: true
            });

        } catch (error) {

            setError(
                error.message ||
                "Invalid email or password"
            );

        } finally {

            setLoading(false);
        }
    };


    return (

        <div className="auth-page">

            <div className="auth-card">

                {/* Logo */}

                <Link
                    to="/"
                    className="auth-logo"
                >
                    Doc<span>GPT</span>
                </Link>


                {/* Heading */}

                <h1 className="auth-title">
                    Welcome back
                </h1>

                <p className="auth-subtitle">
                    Sign in to continue chatting
                    with your documents.
                </p>


                {/* Form */}

                <form
                    className="auth-form"
                    onSubmit={handleSubmit}
                >

                    {/* Email */}

                    <div className="auth-field">

                        <label>
                            Email
                        </label>

                        <input
                            type="email"
                            placeholder="you@example.com"
                            value={email}
                            onChange={(e) =>
                                setEmail(e.target.value)
                            }
                            required
                        />

                    </div>


                    {/* Password */}

                    <div className="auth-field">

                        <label>
                            Password
                        </label>

                        <input
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) =>
                                setPassword(e.target.value)
                            }
                            required
                        />

                    </div>


                    {/* Error */}

                    {error && (

                        <div className="auth-error">
                            {error}
                        </div>

                    )}


                    {/* Submit */}

                    <button
                        type="submit"
                        className="auth-submit"
                        disabled={loading}
                    >

                        {loading
                            ? "Signing in..."
                            : "Sign In"
                        }

                    </button>

                </form>


                {/* Signup */}

                <p className="auth-switch">

                    Don't have an account?{" "}

                    <Link to="/signup">
                        Create an account
                    </Link>

                </p>


                {/* Home */}

                <Link
                    to="/"
                    className="auth-back-home"
                >
                    ← Back to DocGPT
                </Link>

            </div>

        </div>
    );
}

export default Login;