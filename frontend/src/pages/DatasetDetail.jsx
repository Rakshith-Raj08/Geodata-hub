import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { fetchDataset } from '../api.js'

function RichText({ text }) {
  if (!text) return null
  const blocks = text.split('\n\n')
  return (
    <>
      {blocks.map((block, i) => {
        const lines = block.split('\n').filter(Boolean)
        const isList = lines.length > 0 && lines.every((l) => l.trim().startsWith('- '))
        if (isList) {
          return (
            <ul className="rich-list" key={i}>
              {lines.map((l, j) => <li key={j}>{l.replace(/^- /, '')}</li>)}
            </ul>
          )
        }
        return <p className="rich-para" key={i}>{block}</p>
      })}
    </>
  )
}

export default function DatasetDetail() {
  const { slug } = useParams()
  const [dataset, setDataset] = useState(null)
  const [status, setStatus] = useState('loading')

  useEffect(() => {
    setStatus('loading')
    fetchDataset(slug)
      .then((data) => { setDataset(data); setStatus('ready') })
      .catch(() => setStatus('error'))
  }, [slug])

  let sourceLinks = []
  if (dataset?.sourceLinksJson) {
    try { sourceLinks = JSON.parse(dataset.sourceLinksJson) } catch { sourceLinks = [] }
  }

  let findings = []
  if (dataset?.findingsJson) {
    try { findings = JSON.parse(dataset.findingsJson) } catch { findings = [] }
  }

  return (
    <div className="container">
      <Link to="/" className="back-link">&larr; All datasets</Link>

      {status === 'loading' && <p className="loading">Loading…</p>}
      {status === 'error' && <p className="error-text">Couldn't find that dataset.</p>}

      {status === 'ready' && dataset && (
        <>
          <div className="detail-header">
            {dataset.category && (
              <div className="badge-row">
                <span className="badge">{dataset.category}</span>
              </div>
            )}
            <h1>{dataset.title}</h1>
            <p>{dataset.description}</p>
          </div>

          <div className="detail-layout">
            <div>
              {dataset.mapUrl ? (
                <iframe className="map-frame" src={dataset.mapUrl} title={dataset.title} />
              ) : (
                <div className="map-frame map-placeholder">Map coming soon</div>
              )}
            </div>

            <div className="sidebar">
              {dataset.downloadNote && (
                <div className="restricted-note">{dataset.downloadNote}</div>
              )}

              {(dataset.downloadUrl || dataset.rawDownloadUrl || dataset.repoUrl) && (
                <div className="download-group">
                  {dataset.downloadUrl && (
                    <a className="download-btn" href={dataset.downloadUrl} download>
                      Download processed data
                    </a>
                  )}
                  {dataset.rawDownloadUrl && (
                    <a className="download-btn secondary" href={dataset.rawDownloadUrl} download>
                      Download raw data
                    </a>
                  )}
                  {dataset.repoUrl && (
                    <a className="download-btn secondary" href={dataset.repoUrl} target="_blank" rel="noreferrer">
                      View code on GitHub
                    </a>
                  )}
                </div>
              )}

              <div className="meta-table">
                {sourceLinks.length > 0 && (
                  <div className="meta-row">
                    <span className="meta-label">Source</span>
                    <ul className="source-list">
                      {sourceLinks.map((s) => (
                        <li key={s.name}>
                          {s.url ? <a href={s.url} target="_blank" rel="noreferrer">{s.name}</a> : s.name}
                        </li>
                      ))}
                    </ul>
                  </div>
                )}
                {dataset.license && (
                  <div className="meta-row">
                    <span className="meta-label">License</span>
                    <span className="meta-value">{dataset.license}</span>
                  </div>
                )}
                {dataset.dateAdded && (
                  <div className="meta-row">
                    <span className="meta-label">Added</span>
                    <span className="meta-value">{dataset.dateAdded}</span>
                  </div>
                )}
              </div>
            </div>
          </div>

          {dataset.introText && (
            <section className="content-section">
              <h2 className="section-heading">About this project</h2>
              <RichText text={dataset.introText} />
            </section>
          )}

          {dataset.methodology && (
            <section className="content-section">
              <h2 className="section-heading">How it was built</h2>
              <RichText text={dataset.methodology} />
            </section>
          )}

          {findings.length > 0 && (
            <section className="content-section">
              <h2 className="section-heading">Key findings</h2>
              <div className="findings-list">
                {findings.map((f, i) => (
                  <div className="finding-block" key={i}>
                    <h3>{f.title}</h3>
                    <p>{f.text}</p>
                    {f.imageUrl && (
                      <img className="finding-image" src={f.imageUrl} alt={f.title} />
                    )}
                  </div>
                ))}
              </div>
            </section>
          )}

          {dataset.limitations && (
            <section className="content-section limitations-section">
              <h2 className="section-heading">Limitations</h2>
              <RichText text={dataset.limitations} />
            </section>
          )}
        </>
      )}
    </div>
  )
}