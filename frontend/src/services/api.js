import axios from 'axios';

// Use environment variable for API URL, fallback to localhost for development
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Debug: Log the API URL being used
console.log('🔧 API Configuration:', {
    VITE_API_URL: import.meta.env.VITE_API_URL,
    API_BASE_URL: API_BASE_URL,
    allEnvVars: import.meta.env
});

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const analyzeGitHubProfile = async (username) => {
    try {
        const response = await api.post('/analyze', { username });
        return response.data;
    } catch (error) {
        console.error('Error analyzing profile:', error);
        throw error;
    }
};

export const checkHealth = async () => {
    const response = await api.get('/health');
    return response.data;
};

