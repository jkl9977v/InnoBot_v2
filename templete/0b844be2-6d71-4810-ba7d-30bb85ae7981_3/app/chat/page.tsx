
'use client';
import { useState, useRef, useEffect } from 'react';
import Link from 'next/link';

interface Message {
  id: string;
  content: string;
  isUser: boolean;
  timestamp: Date;
}

interface ChatSession {
  id: string;
  title: string;
  lastMessage: string;
  timestamp: Date;
}

export default function ChatPage() {
  const [messages, setMessages] = useState<Message[]>([
    {
      id: '1',
      content: '안녕하세요! 티움봇입니다. 무엇을 도와드릴까요?',
      isUser: false,
      timestamp: new Date()
    }
  ]);
  const [inputValue, setInputValue] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const [chatSessions] = useState<ChatSession[]>([
    {
      id: '1',
      title: '새로운 대화',
      lastMessage: '안녕하세요! 티움봇입니다.',
      timestamp: new Date()
    },
    {
      id: '2',
      title: '제품 문의',
      lastMessage: '제품에 대해 더 자세히 알려주세요.',
      timestamp: new Date(Date.now() - 1000 * 60 * 30)
    },
    {
      id: '3',
      title: '가격 정보',
      lastMessage: '가격표를 확인해드릴게요.',
      timestamp: new Date(Date.now() - 1000 * 60 * 60 * 2)
    }
  ]);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const handleSendMessage = async () => {
    if (!inputValue.trim()) return;

    const userMessage: Message = {
      id: Date.now().toString(),
      content: inputValue,
      isUser: true,
      timestamp: new Date()
    };

    setMessages(prev => [...prev, userMessage]);
    setInputValue('');
    setIsLoading(true);

    // 봇 응답 시뮬레이션
    setTimeout(() => {
      const botMessage: Message = {
        id: (Date.now() + 1).toString(),
        content: `네, "${inputValue}"에 대해 답변드리겠습니다. 더 구체적인 정보가 필요하시면 언제든 말씀해주세요!`,
        isUser: false,
        timestamp: new Date()
      };
      setMessages(prev => [...prev, botMessage]);
      setIsLoading(false);
    }, 1000);
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage();
    }
  };

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar */}
      <div className={`${isSidebarOpen ? 'w-64' : 'w-0'} transition-all duration-300 bg-gray-900 text-white flex flex-col overflow-hidden`}>
        <div className="p-4 border-b border-gray-700">
          <Link href="/" className="flex items-center space-x-2 mb-4">
            <span className="font-['Pacifico'] text-xl text-indigo-400">TiumBot</span>
          </Link>
          <button className="w-full flex items-center justify-center px-4 py-2 bg-indigo-600 hover:bg-indigo-700 rounded-lg transition-colors cursor-pointer whitespace-nowrap">
            <i className="ri-add-line w-4 h-4 flex items-center justify-center mr-2"></i>
            새 대화
          </button>
        </div>

        <div className="flex-1 overflow-y-auto p-2">
          <div className="space-y-1">
            {chatSessions.map((session) => (
              <div
                key={session.id}
                className="p-3 rounded-lg hover:bg-gray-800 cursor-pointer transition-colors group"
              >
                <div className="flex items-center justify-between">
                  <div className="flex-1 min-w-0">
                    <p className="text-sm font-medium text-white truncate">
                      {session.title}
                    </p>
                    <p className="text-xs text-gray-400 truncate mt-1">
                      {session.lastMessage}
                    </p>
                  </div>
                  <button className="opacity-0 group-hover:opacity-100 p-1 hover:bg-gray-700 rounded transition-all cursor-pointer">
                    <i className="ri-more-line w-4 h-4 flex items-center justify-center text-gray-400"></i>
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="p-4 border-t border-gray-700">
          <Link href="/manage" className="flex items-center space-x-2 p-2 hover:bg-gray-800 rounded-lg transition-colors cursor-pointer">
            <i className="ri-settings-3-line w-5 h-5 flex items-center justify-center text-gray-400"></i>
            <span className="text-sm text-gray-300">챗봇 관리</span>
          </Link>
        </div>
      </div>

      {/* Main Chat Area */}
      <div className="flex-1 flex flex-col">
        {/* Header */}
        <div className="bg-white border-b border-gray-200 px-4 py-3 flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <button
              onClick={() => setIsSidebarOpen(!isSidebarOpen)}
              className="p-2 hover:bg-gray-100 rounded-lg transition-colors cursor-pointer"
            >
              <i className="ri-menu-line w-5 h-5 flex items-center justify-center text-gray-600"></i>
            </button>
            <div className="flex items-center space-x-2">
              <div className="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center">
                <i className="ri-robot-line text-indigo-600"></i>
              </div>
              <span className="font-medium text-gray-900">TiumBot</span>
              <span className="text-xs text-green-500 bg-green-100 px-2 py-1 rounded-full">온라인</span>
            </div>
          </div>
          <div className="flex items-center space-x-2">
            <button className="p-2 hover:bg-gray-100 rounded-lg transition-colors cursor-pointer">
              <i className="ri-refresh-line w-5 h-5 flex items-center justify-center text-gray-600"></i>
            </button>
            <button className="p-2 hover:bg-gray-100 rounded-lg transition-colors cursor-pointer">
              <i className="ri-more-line w-5 h-5 flex items-center justify-center text-gray-600"></i>
            </button>
          </div>
        </div>

        {/* Messages */}
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {messages.map((message) => (
            <div
              key={message.id}
              className={`flex ${message.isUser ? 'justify-end' : 'justify-start'}`}
            >
              <div className={`flex max-w-[80%] ${message.isUser ? 'flex-row-reverse' : 'flex-row'} items-start space-x-2`}>
                <div className={`flex-shrink-0 w-8 h-8 rounded-full flex items-center justify-center ${message.isUser ? 'bg-indigo-600 ml-2' : 'bg-gray-200 mr-2'}`}>
                  {message.isUser ? (
                    <i className="ri-user-line text-white text-sm"></i>
                  ) : (
                    <i className="ri-robot-line text-gray-600 text-sm"></i>
                  )}
                </div>
                <div className={`rounded-2xl px-4 py-3 ${message.isUser ? 'bg-indigo-600 text-white' : 'bg-white border border-gray-200 text-gray-900'}`}>
                  <p className="text-sm leading-relaxed whitespace-pre-wrap">{message.content}</p>
                  <p className={`text-xs mt-1 ${message.isUser ? 'text-indigo-200' : 'text-gray-500'}`}>
                    {message.timestamp.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                  </p>
                </div>
              </div>
            </div>
          ))}
          
          {isLoading && (
            <div className="flex justify-start">
              <div className="flex items-start space-x-2 max-w-[80%]">
                <div className="flex-shrink-0 w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center mr-2">
                  <i className="ri-robot-line text-gray-600 text-sm"></i>
                </div>
                <div className="bg-white border border-gray-200 rounded-2xl px-4 py-3">
                  <div className="flex space-x-1">
                    <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
                    <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{ animationDelay: '0.1s' }}></div>
                    <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style={{ animationDelay: '0.2s' }}></div>
                  </div>
                </div>
              </div>
            </div>
          )}
          <div ref={messagesEndRef} />
        </div>

        {/* Input Area */}
        <div className="bg-white border-t border-gray-200 p-4">
          <div className="max-w-4xl mx-auto">
            <div className="flex items-end space-x-3">
              <div className="flex-1 relative">
                <textarea
                  value={inputValue}
                  onChange={(e) => setInputValue(e.target.value)}
                  onKeyPress={handleKeyPress}
                  placeholder="메시지를 입력하세요..."
                  className="w-full resize-none border border-gray-300 rounded-xl px-4 py-3 pr-12 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent max-h-32 text-sm"
                  rows={1}
                  style={{ minHeight: '44px' }}
                />
                <button
                  onClick={handleSendMessage}
                  disabled={!inputValue.trim() || isLoading}
                  className="absolute right-2 bottom-2 p-2 bg-indigo-600 hover:bg-indigo-700 disabled:bg-gray-300 rounded-lg transition-colors cursor-pointer"
                >
                  <i className="ri-send-plane-line w-4 h-4 flex items-center justify-center text-white"></i>
                </button>
              </div>
            </div>
            <p className="text-xs text-gray-500 text-center mt-2">
              Enter로 전송, Shift+Enter로 줄바꿈
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
