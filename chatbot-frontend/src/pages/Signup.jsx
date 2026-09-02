import { useState } from "react";
import { signup } from "../services/authService";
import { useNavigate, Link } from "react-router-dom";

import "./Auth.css";


function Signup() {

    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);


    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");
        setLoading(true);

        try {

            await signup({
                name,
                email,
                password
            });

            /*
             * After successful registration,
             * send user to login.
             */

            navigate("/login", {
                replace: true
            });

        } catch (error) {

            setError(
                error.message ||
                "Unable to create account"
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
                    Create your account
                </h1>

                <p className="auth-subtitle">
                    Start chatting with your
                    documents using AI.
                </p>


                {/* Form */}

                <form
                    className="auth-form"
                    onSubmit={handleSubmit}
                >

                    {/* Name */}

                    <div className="auth-field">

                        <label>
                            Name
                        </label>

                        <input
                            type="text"
                            placeholder="Your name"
                            value={name}
                            onChange={(e) =>
                                setName(e.target.value)
                            }
                            required
                        />

                    </div>


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
                            placeholder="Create a password"
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
                            ? "Creating account..."
                            : "Create Account"
                        }

                    </button>

                </form>


                {/* Login */}

                <p className="auth-switch">

                    Already have an account?{" "}

                    <Link to="/login">
                        Sign in
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

export default Signup;