import { Application } from 'express';
import { createProxyMiddleware } from 'http-proxy-middleware';

export default function (app: Application) {
  app.use(
    '/api/v1',
    createProxyMiddleware({
      target: 'http://localhost:8081',
      changeOrigin: true,
    })
  );
}