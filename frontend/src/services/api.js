import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

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

