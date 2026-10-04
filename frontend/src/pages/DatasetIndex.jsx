import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { fetchDatasets } from '../api.js'

// Respects Vite's base path if the app is deployed under a sub-path.
// (If you use CRA instead of Vite, replace with process.env.PUBLIC_URL + '/')
const BASE = import.meta.env.BASE_URL || '/'
const img = (file) => `${BASE}images/${file}`

// Local images, keyed by slug (most reliable).
// Slugs match the ones in the datasets JSON.
const IMAGES_BY_SLUG = {
  'hyderabad-water-stress': img('Hyderabad1.jpg'),
  'women-empowerment-archetypes': img('Women1.jpg'),
  'hyderabad-metro-expansion': img('Hyderabad2.jpg'),
}

// Fallback lookup by title, normalized so curly/straight apostrophes,
// casing and extra spaces don't break the match.
const normalize = (s = '') =>
  s.replace(/[’‘]/g, "'").trim().toLowerCase()

const IMAGES_BY_TITLE = {
  [normalize('Hyderabad Water Stress Index')]: img('Hyderabad1.jpg'),
  [normalize('Women’s Empowerment Archetypes in India')]: img('Women1.jpg'),
  [normalize('Hyderabad Metro Expansion')]: img('Hyderabad2.jpg'),
}

const FALLBACK_GRADIENT = 'linear-gradient(135deg, #FB923C, #C2410C)'

function getImageSrc(d) {
  return (
    IMAGES_BY_SLUG[d.slug] ||
    IMAGES_BY_TITLE[normalize(d.title)] ||
    d.image_url ||
    ''
  )
}

function thumbStyle(d) {
  const src = getImageSrc(d)
  return {
    backgroundImage: src
      ? `url("${src}"), ${FALLBACK_GRADIENT}`
      : FALLBACK_GRADIENT,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
  }
}

function Mark() {
  return (
    <svg width="34" height="34" viewBox="0 0 34 34" aria-hidden="true">
      <circle cx="17" cy="17" r="16" fill="none" stroke="#EA580C" strokeWidth="1.4" opacity="0.5" />
      <circle cx="17" cy="17" r="10" fill="none" stroke="#EA580C" strokeWidth="1.4" opacity="0.75" />
      <circle cx="17" cy="17" r="3" fill="#EA580C" />
    </svg>
  )
}

export default function DatasetIndex() {
  const [datasets, setDatasets] = useState([])
  const [status, setStatus] = useState('loading')

  useEffect(() => {
    fetchDatasets()
      .then((data) => {
        // TEMP DEBUG: remove once images work
        data.forEach((d) =>
          console.log('[dataset]', JSON.stringify(d.title), d.slug, d.image_url, '->', getImageSrc(d))
        )
        setDatasets(data)
        setStatus('ready')
      })
      .catch(() => setStatus('error'))
  }, [])

  return (
    <div className="container">
      <header className="topbar">
        <div className="topbar-brand">
          <Mark />
          <h1 className="topbar-title">Geodata Hub</h1>
        </div>
        <p className="topbar-sub">
          Raw and processed geospatial datasets, merged, modeled, and mapped for exploration.
        </p>
      </header>

      {status === 'loading' && <p className="loading">Loading datasets…</p>}
      {status === 'error' && (
        <p className="error-text">Couldn't reach the dataset API — check the backend is running.</p>
      )}

      {status === 'ready' && datasets.length === 0 && (
        <div className="empty-state">
          <p>No datasets published yet</p>
          <p>Add one through the API to see it listed here.</p>
        </div>
      )}

      {status === 'ready' && datasets.length > 0 && (
        <div className="grid">
          {datasets.map((d, i) => (
            <Link key={d.id} to={`/datasets/${d.slug}`} className="card">
              <span className="idx">{String(i + 1).padStart(2, '0')}</span>
              <div className="thumb" style={thumbStyle(d)} />
              <div className="body">
                <div className="card-top">
                  <h3>{d.title}</h3>
                  {d.category && <span className="badge">{d.category}</span>}
                </div>
                <p>{d.description}</p>
              </div>
            </Link>
          ))}
        </div>
      )}

      <footer className="site-footer">
        <p>Built by Rakshith Raj</p>
        <div className="footer-links">
          <a href="https://www.linkedin.com/in/Rakshith-Raj08" target="_blank" rel="noreferrer">
            LinkedIn
          </a>
          <a href="https://github.com/Rakshith-Raj08" target="_blank" rel="noreferrer">
            GitHub
          </a>
        </div>
      </footer>
    </div>
  )
}