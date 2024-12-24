const API_BASE = 'http://localhost:8765/quiz-service/quiz';

async function createQuiz(category, numQ, title) {
    const response = await fetch(`${API_BASE}/create?category=${category}&numQ=${numQ}&title=${title}`, {
      method: 'POST',
    });
  
    if (response.headers.get('Content-Type')?.includes('application/json')) {
      return response.json();
    }
    return response.text(); // Handle plain text responses
  }
  
  async function getQuiz(id) {
    const response = await fetch(`${API_BASE}/get/${id}`);
    if (response.headers.get('Content-Type')?.includes('application/json')) {
      return response.json();
    }
    return response.text();
  }
  
  async function submitQuiz(id, responses) {
    const response = await fetch(`${API_BASE}/submit`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(responses),
    });
  
    if (response.headers.get('Content-Type')?.includes('application/json')) {
      return response.json();
    }
    return response.text();
  }

  async function fetchAllQuizzes() {
    try {
        const response = await fetch(`${API_BASE}/getAll`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }

        const quizzes = await response.json();
        return quizzes;
    } catch (error) {
        console.error('Failed to fetch all quizzes:', error);
        return [];
    }
}
  
