
  var fileData = {
    '기술문서': [
      { name: '매뉴얼_v1.pdf', size: '2.4MB', date: '2025-07-10' },
      { name: 'API명세서.docx', size: '1.1MB', date: '2025-06-29' }
    ],
    '영업자료': [
      { name: '제안서_최종.pptx', size: '3.5MB', date: '2025-07-03' }
    ],
    '인사정보': [
      { name: '조직도.xlsx', size: '700KB', date: '2025-06-21' },
      { name: '휴가신청서.pdf', size: '512KB', date: '2025-06-18' }
    ],
    '보안기준': [
      { name: '보안정책.docx', size: '812KB', date: '2025-07-08' },
      { name: '계약서.hwp', size: '3.2MB', date: '2025-06-30' }
    ]
  };

  function loadFiles(folder) {
    const list = fileData[folder] || [];
    const tbody = document.getElementById('file-list');
    tbody.innerHTML = '';
    list.forEach(file => {
      const row = `<tr><td>${file.name}</td><td>${file.size}</td><td>${file.date}</td></tr>`;
      tbody.insertAdjacentHTML('beforeend', row);
    });
  }

  // 기본 폴더 로딩
  loadFiles('기술문서');
