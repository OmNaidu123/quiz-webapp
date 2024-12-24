document.addEventListener('DOMContentLoaded', () => {
    const homeSection = document.getElementById('home');
    const quizSection = document.getElementById('quiz');
    const resultsSection = document.getElementById('results');
  
    const createQuizForm = document.getElementById('createQuizForm');
    const questionsContainer = document.getElementById('questionsContainer');
    const submitQuizButton = document.getElementById('submitQuiz');
    const scoreDisplay = document.getElementById('score');
    const restartButton = document.getElementById('restart');
    const fetchQuizButton = document.getElementById('fetchQuizButton');
    const quizIdInput = document.getElementById('quizIdInput');
    const quizIds = document.getElementById('fetchAllQuizzesButton');
    const qLink = document.getElementById('questionPathway');
    const qList = document.getElementById('quizList');
    
    let currentQuizId;
    let userResponses = [];
    let questionIds = [];

    fetchQuizButton.addEventListener('click', async () => {
    const quizId = quizIdInput.value.trim();
    if (!quizId) {
        alert('Please enter a Quiz ID.');
        return;
    }

    const quizData = await getQuiz(quizId);

    if (Array.isArray(quizData)) {
        const title = `Quiz #${quizId}`; // Placeholder title
        loadQuiz(quizData, title);
    } else {
        console.error('Unexpected response format:', quizData);
        alert('Failed to load the quiz. Please ensure the Quiz ID is correct.');
    }
    });

  
    createQuizForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const category = document.getElementById('category').value;
        const numQ = document.getElementById('numQ').value;
        const title = document.getElementById('title').value;
      
        const quiz = await createQuiz(category, numQ, title);
      
        if (quiz === "success") {
            alert('quiz created');
        }
        else{
            alert('Failed to create quiz. Please try again.');
        }
      });
  
      function loadQuiz(quizData, title) {
        if (!Array.isArray(quizData)) {
            console.error('quizData is not an array:', quizData);
            alert('Error loading quiz. Please try again.');
            return;
        }
    
        homeSection.style.display = 'none';
        quizSection.style.display = 'block';
        resultsSection.style.display = 'none';
        quizIds.style.display = 'none';
        qLink.style.display = 'none';
        qList.style.display = 'none';
    
        document.getElementById('quizTitle').innerText = title;
        questionsContainer.innerHTML = '';
        questionIds = []; // Clear question IDs array for fresh load
    
        quizData.forEach((question, index) => {
            questionIds.push(question.id);
            const questionElement = document.createElement('div');
            questionElement.classList.add('question');
            questionElement.innerHTML = `
                <h3>${index + 1}. ${question.questionTitle}</h3>
                <label><input type="radio" name="q${index}" value="${question.option1}"> ${question.option1}</label><br>
                <label><input type="radio" name="q${index}" value="${question.option2}"> ${question.option2}</label><br>
                <label><input type="radio" name="q${index}" value="${question.option3}"> ${question.option3}</label><br>
                <label><input type="radio" name="q${index}" value="${question.option4}"> ${question.option4}</label>
            `;
            questionsContainer.appendChild(questionElement);
        });
    
        submitQuizButton.style.display = 'block';
        submitQuizButton.onclick = handleSubmitQuiz;
    }    
      
  
    async function handleSubmitQuiz() {
        const questions = document.querySelectorAll('.question');
        let allQuestionsAnswered = true;
    
        userResponses = [];
    
        questions.forEach((question, index) => {
            const selectedOption = document.querySelector(`input[name="q${index}"]:checked`);
            
            if (!selectedOption) {
                allQuestionsAnswered = false;
                question.style.border = '2px solid red'; // Highlight unanswered question
            } else {
                question.style.border = 'none'; // Clear highlight if answered
                userResponses.push({
                    id: questionIds[index],
                    response: selectedOption.value
                });
            }
        });
    
        if (!allQuestionsAnswered) {
            alert('Please answer all questions before submitting the quiz.');
            return;
        }
    
        try {
            const score = await submitQuiz(currentQuizId, userResponses);
            showResults(score);
        } catch (error) {
            console.error('Error submitting quiz:', error);
            alert('Failed to submit quiz. Please try again.');
        }
    }
    
  
    function showResults(score) {
      homeSection.style.display = 'none';
      quizSection.style.display = 'none';
      resultsSection.style.display = 'block';
  
      scoreDisplay.innerText = `You scored ${score} points!`;
    }
  
    restartButton.addEventListener('click', () => {
      homeSection.style.display = 'block';
      quizSection.style.display = 'none';
      resultsSection.style.display = 'none';
      quizIds.style.display = 'block';
      qLink.style.display = 'block';
      qList.style.display = 'block';
    });

    document.getElementById('fetchAllQuizzesButton').addEventListener('click', async () => {
        const quizListContainer = document.getElementById('quizList');
        quizListContainer.innerHTML = ''; // Clear previous list
    
        const quizzes = await fetchAllQuizzes();
    
        if (quizzes.length === 0) {
            quizListContainer.innerHTML = '<li>No quizzes found.</li>';
            return;
        }
    
        quizzes.forEach(quiz => {
            const listItem = document.createElement('li');
            listItem.textContent = `ID: ${quiz.quizId}, Title: ${quiz.quizTitle}`;
            quizListContainer.appendChild(listItem);
        });
    });
    
  });
  