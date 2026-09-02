import { Link } from "react-router-dom";
import "./Home.css";

function Home() {
    return (
        <div className="home-page">

            {/* ================= NAVBAR ================= */}

            <nav className="home-navbar">

                <Link to="/" className="brand">
                    Doc<span>GPT</span>
                </Link>

                <div className="nav-links">
                    <Link to="/chat">
                        <span>💬</span>
                        Chat
                    </Link>

                    <Link to="/chat">
                        <span>↥</span>
                        Upload
                    </Link>
                </div>

                <div className="nav-auth">

                    <Link
                        to="/login"
                        className="signin-link"
                    >
                        Sign In
                    </Link>

                    <Link
                        to="/signup"
                        className="signup-button"
                    >
                        Sign Up
                    </Link>

                </div>

            </nav>


            {/* ================= HERO ================= */}

            <main className="hero">

                <div className="hero-content">

                    {/* Badge */}

                    <div className="hero-badge">
                        <span>✦</span>
                        AI-POWERED DOCUMENT ASSISTANT
                    </div>


                    {/* Heading */}

                    <h1>
                        Research,
                        <br />
                       Understand and Discover
                        <br />

                        <span className="gradient-text">
                            — Smarter
                        </span>
                    </h1>


                    {/* Description */}

                    <p className="hero-description">
                        Bring your documents together and interact with them using AI. DocGPT helps you find answers, summarize information, compare sources, and turn scattered content into useful insights.
                    </p>


                    {/* Buttons */}

                    <div className="hero-buttons">

                        <Link
                            to="/signup"
                            className="primary-button"
                        >
                            Get Started
                            <span>→</span>
                        </Link>

                        <Link
                            to="/chat"
                            className="secondary-button"
                        >
                            Try Demo
                            <span>▤</span>
                        </Link>

                    </div>

                </div>


                {/* ================= ROBOT ================= */}

                <div className="hero-visual">

                    <div className="robot-glow"></div>

                    <div className="robot-container">

                        {/* antenna */}

                        <div className="robot-antenna">
                            <div className="antenna-dot"></div>
                            <div className="antenna-line"></div>
                        </div>

                        {/* head */}

                        <div className="robot-head">

                            <div className="robot-eye"></div>
                            <div className="robot-eye"></div>

                            <div className="robot-smile"></div>

                        </div>


                        {/* body */}

                        <div className="robot-body">

                            <div className="robot-screen">

                                <div className="screen-line line-one"></div>
                                <div className="screen-line line-two"></div>
                                <div className="screen-line line-three"></div>

                            </div>

                        </div>


                        {/* arms */}

                        <div className="robot-arm robot-arm-left">
                            <div className="robot-hand"></div>
                        </div>

                        <div className="robot-arm robot-arm-right">
                            <div className="robot-hand"></div>
                        </div>


                        {/* legs */}

                        <div className="robot-leg robot-leg-left"></div>
                        <div className="robot-leg robot-leg-right"></div>

                    </div>


                    {/* Floating particles */}

                    <div className="particle particle-one"></div>
                    <div className="particle particle-two"></div>
                    <div className="particle particle-three"></div>
                    <div className="particle particle-four"></div>

                </div>

            </main>

        </div>
    );
}

export default Home;