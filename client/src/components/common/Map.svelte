<script>
    import { onMount } from 'svelte';
    import Map from 'ol/Map';
    import View from 'ol/View';
    import TileLayer from 'ol/layer/Tile';
    import VectorLayer from 'ol/layer/Vector';
    import OSM from 'ol/source/OSM';
    import VectorSource from 'ol/source/Vector';
    import Feature from 'ol/Feature';
    import Point from 'ol/geom/Point';
    import { Icon, Style } from 'ol/style';
    import { useGeographic } from 'ol/proj';
    useGeographic();
  
    export let address;
    let map;
  
    onMount(() => {
      map = new Map({
        target: 'map',
        layers: [
          new TileLayer({
            source: new OSM(),
          }),
        ],
        view: new View({
          center: [0, 0],
          zoom: 2,
        }),
      });
  
      fetch(`https://nominatim.openstreetmap.org/search?q=${address}&format=json`)
        .then(response => response.json())
        .then(data => {
          const [result] = data;
          const [longitude, latitude] = result?.lat && result?.lon ? [result.lon, result.lat] : [0, 0];
          map.getView().setCenter([+longitude, +latitude]);
          map.getView().setZoom(14);
  
          const iconStyle = new Style({
            image: new Icon({
              anchor: [0.5, 1],
              src: 'https://openlayers.org/en/latest/examples/data/icon.png'
            })
          });
          const pointFeature = new Feature({
            geometry: new Point([+longitude, +latitude])
          });
          pointFeature.setStyle(iconStyle);
  
          const vectorLayer = new VectorLayer({
            source: new VectorSource({
              features: [pointFeature]
            })
          });
  
          map.addLayer(vectorLayer);
        });
    });
  </script>
  
  <div id="map" style="width: 100%; height: 400px;" />

  <style>
    ul{
        display: none;
    }
  </style>
  