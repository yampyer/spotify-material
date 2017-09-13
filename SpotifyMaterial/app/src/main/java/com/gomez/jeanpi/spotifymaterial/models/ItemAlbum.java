package com.gomez.jeanpi.spotifymaterial.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemAlbum {
    @SerializedName("album_type")
    private String albumType;
    private List<ItemArtist> artists = null;
    @SerializedName("available_markets")
    private List<String> availableMarkets = null;
    @SerializedName("external_urls")
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    private List<Image> images = null;
    private String name;
    private String type;
    private String uri;

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public List<ItemArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<ItemArtist> artists) {
        this.artists = artists;
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrls externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
