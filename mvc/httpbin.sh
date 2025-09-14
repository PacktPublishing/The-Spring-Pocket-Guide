#!/usr/bin/env bash
docker pull kennethreitz/httpbin
docker run -p 80:80 kennethreitz/httpbin

