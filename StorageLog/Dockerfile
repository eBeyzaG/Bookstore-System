FROM openliberty/open-liberty:full-java11-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
	org.opencontainers.image.authors="Beyza" \
	org.opencontainers.image.vendor="Open Liberty" \
	org.opencontainers.image.url="local" \
	org.opencontainers.image.source="https://github.com/eBeyzaG/Bookstore-System" \
	org.opencontainers.image.version="$VERSION" \
	org.opencontainers.image.revision="$REVISION" \
	vendor="Open Liberty" \
	name="storageLog" \
	version="$VERSION-$REVISION"\
	summary="Storage log service for book store system" \
	description="Image for log history storage service with OpenLiberty" 
	
COPY --chown=1001:0 \
	src/main/liberty/config \
	/config/

COPY --chown=1001:0 \
	target/storageLog.war \
	/config/apps
	
RUN configure.sh
