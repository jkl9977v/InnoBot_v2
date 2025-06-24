import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  //const [count, setCount] = useState(0)
  const [question, setQuestion]=useState('');
  const [chatHistory, setChatHistory]=useState([]); //대화 누적형을 []을 사용하여 한다.
  const [answer, setAnswer] = useState('');
  const [loading, setLoading] = useState(false);
  
  const handleSubmit = async(e) => {
    e.preventDefault();
    if(!question.trim()) return;
    
    const UserMessage = {role: 'user', message: question};
    setChatHistory(prev => [...prev, UserMessage]); //질문 추가
    setQuestion(''); //입력창 초기화
    setLoading(true);
    setAnswer('');
    try{ //답변 요청 시도
      const res = await fetch('http://localhost:8080/chat', {
        method:'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({question: UserMessage.message}),
      });
      const data = await res.json();
      console.log(data);
      const answer={role:'bot', message: data.answer};

      // setAnswer(data.answer);
      setChatHistory((prev) => [...prev, answer]); //답변 추가
    }catch(err){ //답변 요청이 안됐을때
      console.error(err);
      //const errorMessage = {role: 'bot', message: '오류가 발생했습니다.'}
      //setAnswer('요청 중 오류가 발생했습니다.');
      setChatHistory(prev => [...prev, {role: 'bot', message: '오류가 발생했습니다.'}]);
    }
    finally{ setLoading(false); }
  }

  return (
     <div style={{ maxWidth: 600, margin: '50px auto', fontFamilly:'sans-serif'}}>
      <h1>Inno ChatBot</h1>

      <form onSubmit={handleSubmit}>
        <input 
          type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="질문을 입력하세요"
          style={{ width:'100%', padding: '8px', fontSize:'1rem'}} 
        />
        <button type="submit" disabled={loading || !question.trim()}
          style={{ marginTop: '10px'}}>
          {loading? '로딩 중...': '전송'}
        </button>
        </form>

          <div style={{ marginTop: '20px', whiteSpace: 'pre-wrap'}}>
            {chatHistory.map((chat, idx)=> (
              <div key={idx} style={{
                textAlign: '10px 0',
                backgroundColor: chat.role==='user' ? '#dbeafe' : '#f0f0f0',
                padding: '8px',
                borderRadius: '10px'
              }}>
            <strong>{chat.role ==='user' ? '나':'봇'}</strong>
            <div style={{ whiteSpace: 'pre-wrap'}}>{chat.message}</div>
          </div>
        ))}
        </div>
    </div>
    /*
    <div style={{ maxWidth: 600, margin: '50px auto', fontFamilly:'sans-serif'}}>
      <h1>Inno ChatBot</h1>
      <form onSubmit={handleSubmit}>
        <input type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="질문을 입력하세요"
          style={{ width:'100%', padding: '8px', fontSize:'1rem'}} 
        />
        <button type="submit" disabled={loading || !question.trim()}
        style={{ marginTop: '10px'}}>
          {loading? '로딩 중...': '전송'}
        </button>
        </form>
        {answer && (
          <div style={{ marginTop: '20px', whiteSpace: 'pre-wrap'}}>
            <strong>답변: </strong>
            <p>{answer}</p>
          </div>
        )}
    </div>
    */
  );
}

export default App;
