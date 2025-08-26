
'use client';
import { useState } from 'react';
import Link from 'next/link';

interface BotSettings {
  name: string;
  personality: string;
  language: string;
  responseTime: string;
  maxTokens: number;
  temperature: number;
}

interface AnalyticsData {
  totalConversations: number;
  avgResponseTime: string;
  userSatisfaction: number;
  activeUsers: number;
}

interface FileItem {
  id: string;
  name: string;
  type: 'folder' | 'file';
  size?: string;
  lastModified: Date;
  permission: string;
}

interface User {
  id: string;
  name: string;
  email: string;
  role: string;
  status: 'active' | 'inactive';
  lastAccess: Date;
}

export default function ManagePage() {
  const [activeTab, setActiveTab] = useState<'settings' | 'analytics' | 'training' | 'files' | 'users'>('settings');
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [settings, setSettings] = useState<BotSettings>({
    name: 'TiumBot',
    personality: '친근하고 도움이 되는',
    language: '한국어',
    responseTime: '빠름',
    maxTokens: 2048,
    temperature: 0.7
  });

  const [analytics] = useState<AnalyticsData>({
    totalConversations: 1247,
    avgResponseTime: '1.2초',
    userSatisfaction: 4.8,
    activeUsers: 89
  });

  const [files] = useState<FileItem[]>([
    { id: '1', name: '봇데이터 폴더', type: 'folder', lastModified: new Date('2025-01-02'), permission: '읽기/쓰기' },
    { id: '2', name: '대화 연결 docx', type: 'folder', lastModified: new Date('2025-01-02'), permission: '읽기' },
    { id: '3', name: 'ImoECM_P2.pdf', type: 'file', size: '32227 KB', lastModified: new Date('2025-07-02'), permission: '다운로드' },
    { id: '4', name: 'ImoMark_P2.pdf', type: 'file', size: '17871 KB', lastModified: new Date('2025-07-02'), permission: '다운로드' },
    { id: '5', name: 'LizardBackup_P2.pdf', type: 'file', size: '41974 KB', lastModified: new Date('2025-07-02'), permission: '다운로드' },
    { id: '6', name: 'uPouch_P2.pdf', type: 'file', size: '25451 KB', lastModified: new Date('2025-07-02'), permission: '다운로드' },
    { id: '7', name: 'settings - 복사본.csv', type: 'file', size: '1.1 KB', lastModified: new Date('2025-06-10'), permission: '다운로드' }
  ]);

  const [users] = useState<User[]>([
    { id: '1', name: '김관리자', email: 'admin@tiumbot.com', role: '최고관리자', status: 'active', lastAccess: new Date('2025-01-08') },
    { id: '2', name: '이운영자', email: 'operator@tiumbot.com', role: '운영자', status: 'active', lastAccess: new Date('2025-01-07') },
    { id: '3', name: '박개발자', email: 'dev@tiumbot.com', role: '개발자', status: 'active', lastAccess: new Date('2025-01-06') },
    { id: '4', name: '최사용자', email: 'user@tiumbot.com', role: '일반사용자', status: 'inactive', lastAccess: new Date('2025-01-03') }
  ]);

  const handleSettingsChange = (key: keyof BotSettings, value: string | number) => {
    setSettings(prev => ({ ...prev, [key]: value }));
  };

  const handleSaveSettings = () => {
    console.log('설정이 저장되었습니다:', settings);
  };

  const formatDate = (date: Date) => {
    return date.toLocaleDateString('ko-KR') + ' ' + date.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit' });
  };

  const sidebarItems = [
    { id: 'settings', icon: 'ri-settings-3-line', label: '기본 설정' },
    { id: 'analytics', icon: 'ri-bar-chart-line', label: '분석 및 통계' },
    { id: 'training', icon: 'ri-brain-line', label: '학습 데이터' },
    { id: 'files', icon: 'ri-folder-line', label: '파일 시스템' },
    { id: 'users', icon: 'ri-user-line', label: '사용자 관리' }
  ];

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar */}
      <div className={`${isSidebarOpen ? 'w-64' : 'w-0'} transition-all duration-300 bg-gray-900 text-white flex flex-col overflow-hidden`}>
        <div className="p-4 border-b border-gray-700">
          <Link href="/" className="flex items-center space-x-2 mb-4">
            <span className="font-['Pacifico'] text-xl text-indigo-400">TiumBot</span>
          </Link>
          <div className="text-sm text-gray-400">관리자 대시보드</div>
        </div>

        <div className="flex-1 overflow-y-auto p-2">
          <div className="space-y-1">
            {sidebarItems.map((item) => (
              <button
                key={item.id}
                onClick={() => setActiveTab(item.id as any)}
                className={`w-full flex items-center space-x-3 p-3 rounded-lg transition-colors cursor-pointer ${
                  activeTab === item.id 
                    ? 'bg-indigo-600 text-white' 
                    : 'text-gray-300 hover:bg-gray-800 hover:text-white'
                }`}
              >
                <i className={`${item.icon} w-5 h-5 flex items-center justify-center`}></i>
                <span className="text-sm font-medium">{item.label}</span>
              </button>
            ))}
          </div>
        </div>

        <div className="p-4 border-t border-gray-700">
          <Link href="/chat" className="flex items-center space-x-3 p-3 hover:bg-gray-800 rounded-lg transition-colors cursor-pointer">
            <i className="ri-chat-3-line w-5 h-5 flex items-center justify-center text-gray-400"></i>
            <span className="text-sm text-gray-300">챗봇 테스트</span>
          </Link>
        </div>
      </div>

      {/* Main Content */}
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
            <h1 className="text-xl font-semibold text-gray-900">
              {sidebarItems.find(item => item.id === activeTab)?.label || '관리 대시보드'}
            </h1>
          </div>
          <div className="flex items-center space-x-3">
            <div className="flex items-center space-x-2">
              <div className="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center">
                <i className="ri-user-line text-indigo-600"></i>
              </div>
              <span className="text-sm text-gray-700">관리자</span>
            </div>
          </div>
        </div>

        {/* Content Area */}
        <div className="flex-1 overflow-y-auto p-6">
          {/* Settings Tab */}
          {activeTab === 'settings' && (
            <div className="max-w-4xl">
              <div className="bg-white rounded-xl border border-gray-200 p-6">
                <h2 className="text-lg font-semibold text-gray-900 mb-6">챗봇 기본 설정</h2>
                
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      챗봇 이름
                    </label>
                    <input
                      type="text"
                      value={settings.name}
                      onChange={(e) => handleSettingsChange('name', e.target.value)}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent text-sm"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      성격 설정
                    </label>
                    <select
                      value={settings.personality}
                      onChange={(e) => handleSettingsChange('personality', e.target.value)}
                      className="w-full px-3 py-2 pr-8 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent text-sm"
                    >
                      <option value="친근하고 도움이 되는">친근하고 도움이 되는</option>
                      <option value="전문적이고 공식적인">전문적이고 공식적인</option>
                      <option value="유머러스하고 재미있는">유머러스하고 재미있는</option>
                      <option value="차분하고 신중한">차분하고 신중한</option>
                    </select>
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      주요 언어
                    </label>
                    <select
                      value={settings.language}
                      onChange={(e) => handleSettingsChange('language', e.target.value)}
                      className="w-full px-3 py-2 pr-8 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent text-sm"
                    >
                      <option value="한국어">한국어</option>
                      <option value="영어">영어</option>
                      <option value="다국어">다국어</option>
                    </select>
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      응답 속도
                    </label>
                    <div className="flex space-x-4">
                      {['빠름', '보통', '신중함'].map((speed) => (
                        <label key={speed} className="flex items-center">
                          <input
                            type="radio"
                            name="responseTime"
                            value={speed}
                            checked={settings.responseTime === speed}
                            onChange={(e) => handleSettingsChange('responseTime', e.target.value)}
                            className="mr-2"
                          />
                          <span className="text-sm text-gray-700">{speed}</span>
                        </label>
                      ))}
                    </div>
                  </div>

                  <div className="lg:col-span-2">
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      최대 토큰 수: {settings.maxTokens}
                    </label>
                    <input
                      type="range"
                      min="512"
                      max="4096"
                      step="256"
                      value={settings.maxTokens}
                      onChange={(e) => handleSettingsChange('maxTokens', parseInt(e.target.value))}
                      className="w-full"
                    />
                    <div className="flex justify-between text-xs text-gray-500 mt-1">
                      <span>512</span>
                      <span>4096</span>
                    </div>
                  </div>

                  <div className="lg:col-span-2">
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      창의성 수준: {settings.temperature}
                    </label>
                    <input
                      type="range"
                      min="0"
                      max="1"
                      step="0.1"
                      value={settings.temperature}
                      onChange={(e) => handleSettingsChange('temperature', parseFloat(e.target.value))}
                      className="w-full"
                    />
                    <div className="flex justify-between text-xs text-gray-500 mt-1">
                      <span>보수적</span>
                      <span>창의적</span>
                    </div>
                  </div>
                </div>

                <div className="flex justify-end mt-8">
                  <button
                    onClick={handleSaveSettings}
                    className="px-6 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition-colors cursor-pointer whitespace-nowrap"
                  >
                    설정 저장
                  </button>
                </div>
              </div>
            </div>
          )}

          {/* Analytics Tab */}
          {activeTab === 'analytics' && (
            <div className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                <div className="bg-white rounded-xl border border-gray-200 p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">총 대화 수</p>
                      <p className="text-2xl font-bold text-gray-900">{analytics.totalConversations.toLocaleString()}</p>
                    </div>
                    <div className="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
                      <i className="ri-chat-3-line text-blue-600 text-xl"></i>
                    </div>
                  </div>
                  <div className="mt-4 flex items-center">
                    <span className="text-sm text-green-600">+12%</span>
                    <span className="text-sm text-gray-500 ml-2">지난 주 대비</span>
                  </div>
                </div>

                <div className="bg-white rounded-xl border border-gray-200 p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">평균 응답 시간</p>
                      <p className="text-2xl font-bold text-gray-900">{analytics.avgResponseTime}</p>
                    </div>
                    <div className="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center">
                      <i className="ri-timer-line text-green-600 text-xl"></i>
                    </div>
                  </div>
                  <div className="mt-4 flex items-center">
                    <span className="text-sm text-green-600">-5%</span>
                    <span className="text-sm text-gray-500 ml-2">지난 주 대비</span>
                  </div>
                </div>

                <div className="bg-white rounded-xl border border-gray-200 p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">만족도</p>
                      <p className="text-2xl font-bold text-gray-900">{analytics.userSatisfaction}/5.0</p>
                    </div>
                    <div className="w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center">
                      <i className="ri-star-line text-yellow-600 text-xl"></i>
                    </div>
                  </div>
                  <div className="mt-4 flex items-center">
                    <span className="text-sm text-green-600">+0.2</span>
                    <span className="text-sm text-gray-500 ml-2">지난 주 대비</span>
                  </div>
                </div>

                <div className="bg-white rounded-xl border border-gray-200 p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600">활성 사용자</p>
                      <p className="text-2xl font-bold text-gray-900">{analytics.activeUsers}</p>
                    </div>
                    <div className="w-12 h-12 bg-purple-100 rounded-full flex items-center justify-center">
                      <i className="ri-user-line text-purple-600 text-xl"></i>
                    </div>
                  </div>
                  <div className="mt-4 flex items-center">
                    <span className="text-sm text-green-600">+8%</span>
                    <span className="text-sm text-gray-500 ml-2">지난 주 대비</span>
                  </div>
                </div>
              </div>

              <div className="bg-white rounded-xl border border-gray-200 p-6">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">최근 대화 분석</h3>
                <div className="space-y-4">
                  <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                    <div>
                      <p className="font-medium text-gray-900">가장 많이 묻는 질문</p>
                      <p className="text-sm text-gray-600">"제품 가격이 얼마인가요?"</p>
                    </div>
                    <span className="text-sm font-medium text-indigo-600">47회</span>
                  </div>
                  <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                    <div>
                      <p className="font-medium text-gray-900">평균 대화 길이</p>
                      <p className="text-sm text-gray-600">사용자당 5.2개 메시지</p>
                    </div>
                    <span className="text-sm font-medium text-green-600">+0.8</span>
                  </div>
                  <div className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                    <div>
                      <p className="font-medium text-gray-900">해결률</p>
                      <p className="text-sm text-gray-600">사용자 문의 중 해결된 비율</p>
                    </div>
                    <span className="text-sm font-medium text-blue-600">92%</span>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* Training Tab */}
          {activeTab === 'training' && (
            <div className="space-y-6">
              <div className="bg-white rounded-xl border border-gray-200 p-6">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">학습 데이터 관리</h3>
                <p className="text-gray-600 mb-6">
                  챗봇의 성능을 향상시키기 위해 추가 학습 데이터를 업로드하거나 관리할 수 있습니다.
                </p>
                
                <div className="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center">
                  <i className="ri-upload-cloud-line text-4xl text-gray-400 mb-4"></i>
                  <h4 className="text-lg font-medium text-gray-900 mb-2">학습 데이터 업로드</h4>
                  <p className="text-gray-600 mb-4">CSV, JSON, TXT 파일을 지원합니다</p>
                  <button className="px-6 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition-colors cursor-pointer whitespace-nowrap">
                    파일 선택
                  </button>
                </div>
              </div>

              <div className="bg-white rounded-xl border border-gray-200 p-6">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">기존 학습 데이터</h3>
                <div className="space-y-3">
                  <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                    <div className="flex items-center space-x-3">
                      <i className="ri-file-text-line text-gray-400"></i>
                      <div>
                        <p className="font-medium text-gray-900">기본 FAQ 데이터</p>
                        <p className="text-sm text-gray-600">250개의 질문-답변 쌍</p>
                      </div>
                    </div>
                    <div className="flex items-center space-x-2">
                      <span className="text-sm text-green-600 bg-green-100 px-2 py-1 rounded-full">활성</span>
                      <button className="p-2 hover:bg-gray-100 rounded-lg transition-colors cursor-pointer">
                        <i className="ri-more-line w-4 h-4 flex items-center justify-center text-gray-400"></i>
                      </button>
                    </div>
                  </div>
                  
                  <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                    <div className="flex items-center space-x-3">
                      <i className="ri-file-text-line text-gray-400"></i>
                      <div>
                        <p className="font-medium text-gray-900">제품 정보 데이터</p>
                        <p className="text-sm text-gray-600">128개의 제품 설명</p>
                      </div>
                    </div>
                    <div className="flex items-center space-x-2">
                      <span className="text-sm text-green-600 bg-green-100 px-2 py-1 rounded-full">활성</span>
                      <button className="p-2 hover:bg-gray-100 rounded-lg transition-colors cursor-pointer">
                        <i className="ri-more-line w-4 h-4 flex items-center justify-center text-gray-400"></i>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* Files Tab */}
          {activeTab === 'files' && (
            <div className="bg-white rounded-xl border border-gray-200">
              <div className="p-6 border-b border-gray-200">
                <div className="flex items-center justify-between mb-4">
                  <h3 className="text-lg font-semibold text-gray-900">파일 시스템</h3>
                  <button className="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition-colors cursor-pointer whitespace-nowrap text-sm">
                    <i className="ri-add-line w-4 h-4 flex items-center justify-center mr-2 inline-flex"></i>
                    새 폴더 생성
                  </button>
                </div>
                
                <div className="flex items-center space-x-4 text-sm text-gray-600">
                  <span>현재 경로: D:/InnoBiot_v3/docs</span>
                  <div className="flex items-center space-x-2">
                    <label>정렬:</label>
                    <select className="px-2 py-1 border border-gray-300 rounded text-sm pr-8">
                      <option>이름 순으로</option>
                      <option>수정일순</option>
                      <option>크기순</option>
                    </select>
                    <select className="px-2 py-1 border border-gray-300 rounded text-sm pr-8">
                      <option>10</option>
                      <option>25</option>
                      <option>50</option>
                    </select>
                  </div>
                </div>
              </div>

              <div className="overflow-x-auto">
                <table className="w-full">
                  <thead className="bg-gray-50 border-b border-gray-200">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        파일 / 폴더명
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        크기
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        수정일
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        권한
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        작업
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200">
                    {files.map((file) => (
                      <tr key={file.id} className="hover:bg-gray-50 transition-colors">
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center space-x-3">
                            <i className={`${file.type === 'folder' ? 'ri-folder-fill text-blue-500' : 'ri-file-text-fill text-gray-400'} text-lg`}></i>
                            <span className="text-sm font-medium text-gray-900">{file.name}</span>
                          </div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                          {file.size || '-'}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                          {formatDate(file.lastModified)}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                            {file.permission}
                          </span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                          <div className="flex items-center space-x-2">
                            <button className="text-indigo-600 hover:text-indigo-900 transition-colors cursor-pointer">
                              다운로드
                            </button>
                            <button className="text-gray-400 hover:text-gray-600 transition-colors cursor-pointer">
                              <i className="ri-more-line w-4 h-4 flex items-center justify-center"></i>
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>

              <div className="px-6 py-3 border-t border-gray-200 bg-gray-50">
                <div className="flex items-center justify-between text-sm text-gray-600">
                  <span>총 {files.length}개 항목</span>
                  <div className="flex items-center space-x-2">
                    <button className="px-3 py-1 border border-gray-300 rounded hover:bg-gray-50 transition-colors cursor-pointer">이전</button>
                    <span>1 / 1</span>
                    <button className="px-3 py-1 border border-gray-300 rounded hover:bg-gray-50 transition-colors cursor-pointer">다음</button>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* Users Tab */}
          {activeTab === 'users' && (
            <div className="bg-white rounded-xl border border-gray-200">
              <div className="p-6 border-b border-gray-200">
                <div className="flex items-center justify-between mb-4">
                  <h3 className="text-lg font-semibold text-gray-900">사용자 관리</h3>
                  <button className="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition-colors cursor-pointer whitespace-nowrap text-sm">
                    <i className="ri-user-add-line w-4 h-4 flex items-center justify-center mr-2 inline-flex"></i>
                    사용자 추가
                  </button>
                </div>
                
                <div className="flex items-center space-x-4">
                  <div className="flex-1 relative">
                    <i className="ri-search-line absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                    <input
                      type="text"
                      placeholder="사용자 검색..."
                      className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent text-sm"
                    />
                  </div>
                  <select className="px-3 py-2 border border-gray-300 rounded-lg text-sm pr-8">
                    <option>모든 역할</option>
                    <option>관리자</option>
                    <option>운영자</option>
                    <option>일반사용자</option>
                  </select>
                </div>
              </div>

              <div className="overflow-x-auto">
                <table className="w-full">
                  <thead className="bg-gray-50 border-b border-gray-200">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        사용자
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        역할
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        상태
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        마지막 접속
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        작업
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200">
                    {users.map((user) => (
                      <tr key={user.id} className="hover:bg-gray-50 transition-colors">
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center space-x-3">
                            <div className="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center">
                              <i className="ri-user-line text-indigo-600 text-sm"></i>
                            </div>
                            <div>
                              <div className="text-sm font-medium text-gray-900">{user.name}</div>
                              <div className="text-sm text-gray-500">{user.email}</div>
                            </div>
                          </div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                            user.role === '최고관리자' ? 'bg-red-100 text-red-800' :
                            user.role === '운영자' ? 'bg-blue-100 text-blue-800' :
                            user.role === '개발자' ? 'bg-purple-100 text-purple-800' :
                            'bg-gray-100 text-gray-800'
                          }`}>
                            {user.role}
                          </span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                            user.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'
                          }`}>
                            {user.status === 'active' ? '활성' : '비활성'}
                          </span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                          {formatDate(user.lastAccess)}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                          <div className="flex items-center space-x-2">
                            <button className="text-indigo-600 hover:text-indigo-900 transition-colors cursor-pointer">
                              편집
                            </button>
                            <button className="text-red-600 hover:text-red-900 transition-colors cursor-pointer">
                              삭제
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>

              <div className="px-6 py-3 border-t border-gray-200 bg-gray-50">
                <div className="flex items-center justify-between text-sm text-gray-600">
                  <span>총 {users.length}명의 사용자</span>
                  <div className="flex items-center space-x-2">
                    <button className="px-3 py-1 border border-gray-300 rounded hover:bg-gray-50 transition-colors cursor-pointer">이전</button>
                    <span>1 / 1</span>
                    <button className="px-3 py-1 border border-gray-300 rounded hover:bg-gray-50 transition-colors cursor-pointer">다음</button>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
