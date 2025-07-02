import { useState, useRef, useEffect } from 'react';
import './App.css';
import botIcon from './image/tiumBot1.png';

function App() {
  const [question, setQuestion] = useState('');
  const [chatHistory, setChatHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const messagesEndRef = useRef(null);

  //const textareaRef=useRef(null);
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!question.trim()) return;

    const userMessage = { role: 'user', message: question };
    setChatHistory((prev) => [...prev, userMessage]);
    setQuestion('');
    setLoading(true);

    try {
      const res = await fetch('http://192.168.11.146:8080/chat', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ question: userMessage.message }),
      });

      const data = await res.json();
      const botMessage = { role: 'bot', message: data.answer };
      setChatHistory((prev) => [...prev, botMessage]);
    } catch (err) {
      console.error(err);
      setChatHistory((prev) => [...prev, { role: 'bot', message: '오류가 발생했습니다.' }]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [chatHistory]);

  return (
    <div className="chat-wrapper">
      <div className="chat-container">
        <div className="chat-header">
          <img src={botIcon} alt="Bot" className="bot-avatar"></img>
          <span>TiumBot</span>
        </div>

        <div className="chat-messages">
          {chatHistory.map((chat, idx) => (
            <div
              key={idx}
              className={`chat-bubble ${chat.role === 'user' ? 'user' : 'bot'}`}
            >
              {chat.message}
            </div>
          ))}
          <div ref={messagesEndRef} />
        </div>

        <form className="chat-input-area" 
          // onSubmit={ (e) => {
          //   e.preventDefault(); //기본 제출(get) 동작 막기
          //   handleSubmit(e); //직접 처리
          // /}}  action="" //기본 form 경로 제거, 이중 안전장치
          >
          <textarea className="chat-textarea"
            //ref={textareRef}
            type="text"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            rows={1}
            placeholder="질문을 입력하세요"
          />
          <button type="button" onClick={handleSubmit} disabled={loading || !question.trim()}>
            {loading ? '로딩 중...' : '전송'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default App;
