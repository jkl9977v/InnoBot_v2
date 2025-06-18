import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  //const [count, setCount] = useState(0)
  const [question, setQuestion]=useState('');
  const [answer, setAnswer] = useState('');
  const [loading, setLoading] = useState(false);
  
  const handleSubmit = async(e) => {
    e.preventDefault();
    setLoading(true);
    setAnswer('');
    try{ //답변 요청 시도
      const res = await fetch('http://localhost:8080/chat', {
        method:'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({question}),
      });
      const data = await res.json();
      setAnswer(data.answer);
    }catch(err){ //답변 요청이 안됐을때
      console.error(err);
      setAnswer('요청 중 오류가 발생했습니다.');
    }finally{
      setLoading(false);
    }
  }

  return (
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
    /*<>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
    */
  )
}

export default App;
