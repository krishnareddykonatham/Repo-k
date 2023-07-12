import { useState } from 'react';

function App() {
  const [url, setUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [error, setError] = useState('');

  const createShortenedUrl = async () => {
    try {
      const response = await fetch('/api/urls', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ originalUrl: url }),
      });

      const data = await response.json();
      setShortUrl(data.shortUrl);
      setError('');
    } catch (error) {
      setError('Error creating shortened URL');
      console.log(error);
    }
  };

  const visitOriginalUrl = async () => {
    try {
      const response = await fetch(`/api/urls/${shortUrl}`);
      if (response.ok) {
        const data = await response.json();
        window.location.href = data;
      } else {
        setError('URL does not exist or has expired');
      }
    } catch (error) {
      setError('Error visiting URL');
      console.log(error);
    }
  };

  return (
    <div className="App">
      <input type="text" value={url} onChange={(e) => setUrl(e.target.value)} />
      <button onClick={createShortenedUrl}>Create Short URL</button>

      {shortUrl && (
        <div>
          Short URL: <a href={`http://localhost:7070/${shortUrl}`}>{shortUrl}</a>
        </div>
      )}

      {error && <div>{error}</div>}

      <hr />

      <input
        type="text"
        value={shortUrl}
        onChange={(e) => setShortUrl(e.target.value.substring('http://localhost:7070/'.length))}
      />
      <button onClick={visitOriginalUrl}>Visit URL</button>
    </div>
  );
}