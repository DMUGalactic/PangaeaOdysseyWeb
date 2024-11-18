import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Header from "./components/Header";
import Footer from "./components/Footer";
import VideoBackground from "./components/VideoBackground";
import Download from "./Download";
import Info from "./Info";
import Login from "./Login";
import Company from "./Company";
import SignUp from "./SignUp";
import AuthRedirect from "./AuthRedirect";

import Community from "./Community/Community"
import CreatePost from "./Community/CreatePost"
import PostDetail from './Community/PostDetail';
import EditPost from './Community/EditPost';

import "./App.css"

function App() {
  return (
    <Router>
      <AuthProvider>
        <Header />
        <div className="content-container">
          <Routes>
            <Route path="/" element={<VideoBackground />} />
            <Route path="/company" element={<Company />} />
            <Route path="/info" element={<Info />} />
            <Route path="/community" element={<Community />} />
            <Route path="/create-post" element={<CreatePost />} />
            <Route path="/community/:id" element={<PostDetail />} />
            <Route path="/edit-post/:id" element={<EditPost />} />
            <Route path="/download" element={<Download />} />
            <Route path="/login" element={<Login />} />
            <Route path="/sign-up" element={<SignUp />} />
            <Route path="/auth-redirect" element={<AuthRedirect />} />
          </Routes>
        </div>
        <Footer />
      </AuthProvider>
    </Router>
  );
}

export default App;
