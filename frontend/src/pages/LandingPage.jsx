import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Github, Sparkles, TrendingUp } from 'lucide-react';
import { analyzeGitHubProfile } from '../services/api';

export default function LandingPage() {
    const [username, setUsername] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    console.log('LandingPage component loaded!');

    const extractUsername = (input) => {
        if (input.includes('github.com/')) {
            const parts = input.split('github.com/');
            return parts[1].split('/')[0];
        }
        return input.trim();
    };

    const handleAnalyze = async (e) => {
        e.preventDefault();
        setError('');

        if (!username.trim()) {
            setError('Please enter a GitHub username or URL');
            return;
        }

        setLoading(true);
        console.log('Analyzing username:', username);
        
        try {
            const extractedUsername = extractUsername(username);
            console.log('Extracted username:', extractedUsername);
            
            const data = await analyzeGitHubProfile(extractedUsername);
            console.log('Received data from API:', data);
            
            navigate('/dashboard', { state: { data } });
        } catch (err) {
            console.error('Error analyzing profile:', err);
            setError(err.response?.data?.message || 'Failed to analyze profile. Please check the username and try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ minHeight: '100vh', background: 'linear-gradient(to bottom right, #eff6ff, #ffffff, #eff6ff)', padding: '4rem 1rem' }}>
            <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
                {/* Header */}
                <div style={{ textAlign: 'center', marginBottom: '4rem' }}>
                    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '1rem', marginBottom: '1.5rem' }}>
                        <Github size={48} color="#2563eb" />
                        <h1 style={{ fontSize: '3rem', fontWeight: 'bold', color: '#111827', margin: 0 }}>
                            GitHub Portfolio Analyzer
                        </h1>
                    </div>
                    <p style={{ fontSize: '1.25rem', color: '#6b7280', maxWidth: '42rem', margin: '0 auto' }}>
                        Get recruiter-grade insights on any GitHub profile. Powered by AI and deterministic scoring algorithms.
                    </p>
                </div>

                {/* Main Card */}
                <div style={{ maxWidth: '42rem', margin: '0 auto' }}>
                    <div style={{ background: 'white', borderRadius: '0.5rem', boxShadow: '0 1px 3px 0 rgb(0 0 0 / 0.1)', padding: '2rem', border: '1px solid #f3f4f6' }}>
                        <form onSubmit={handleAnalyze} style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
                            <div>
                                <label htmlFor="username" style={{ display: 'block', fontSize: '0.875rem', fontWeight: '500', color: '#374151', marginBottom: '0.5rem' }}>
                                    GitHub Username or Profile URL
                                </label>
                                <input
                                    id="username"
                                    type="text"
                                    style={{ 
                                        width: '100%', 
                                        padding: '0.75rem 1rem', 
                                        border: '1px solid #d1d5db', 
                                        borderRadius: '0.5rem',
                                        fontSize: '1rem',
                                        outline: 'none'
                                    }}
                                    placeholder="e.g., torvalds or https://github.com/torvalds"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    disabled={loading}
                                />
                                {error && (
                                    <p style={{ marginTop: '0.5rem', fontSize: '0.875rem', color: '#dc2626' }}>{error}</p>
                                )}
                            </div>

                            <button
                                type="submit"
                                style={{
                                    width: '100%',
                                    background: loading ? '#9ca3af' : '#2563eb',
                                    color: 'white',
                                    padding: '0.75rem 1.5rem',
                                    borderRadius: '0.5rem',
                                    fontWeight: '500',
                                    border: 'none',
                                    cursor: loading ? 'not-allowed' : 'pointer',
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    gap: '0.5rem',
                                    fontSize: '1rem'
                                }}
                                disabled={loading}
                            >
                                {loading ? (
                                    <>
                                        <div style={{ 
                                            width: '1.25rem', 
                                            height: '1.25rem', 
                                            border: '2px solid white', 
                                            borderTopColor: 'transparent',
                                            borderRadius: '50%',
                                            animation: 'spin 1s linear infinite'
                                        }}></div>
                                        Analyzing...
                                    </>
                                ) : (
                                    <>
                                        <Sparkles size={20} />
                                        Analyze Profile
                                    </>
                                )}
                            </button>
                        </form>
                    </div>

                    {/* Features */}
                    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1.5rem', marginTop: '3rem' }}>
                        <div style={{ textAlign: 'center', padding: '1.5rem' }}>
                            <div style={{ width: '3rem', height: '3rem', background: '#dbeafe', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 1rem' }}>
                                <TrendingUp size={24} color="#2563eb" />
                            </div>
                            <h3 style={{ fontWeight: '600', color: '#111827', marginBottom: '0.5rem' }}>Scoring Engine</h3>
                            <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>6 metrics analyzed with weighted algorithms</p>
                        </div>
                        <div style={{ textAlign: 'center', padding: '1.5rem' }}>
                            <div style={{ width: '3rem', height: '3rem', background: '#dbeafe', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 1rem' }}>
                                <Sparkles size={24} color="#2563eb" />
                            </div>
                            <h3 style={{ fontWeight: '600', color: '#111827', marginBottom: '0.5rem' }}>AI Insights</h3>
                            <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>Recruiter-focused analysis and recommendations</p>
                        </div>
                        <div style={{ textAlign: 'center', padding: '1.5rem' }}>
                            <div style={{ width: '3rem', height: '3rem', background: '#dbeafe', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 1rem' }}>
                                <Github size={24} color="#2563eb" />
                            </div>
                            <h3 style={{ fontWeight: '600', color: '#111827', marginBottom: '0.5rem' }}>GitHub API</h3>
                            <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>Real-time data from public repositories</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
