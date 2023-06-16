FROM nginx:latest

COPY nginx.conf /etc/nginx/nginx.conf
COPY extent-test-output/screenshots /usr/share/nginx/html



