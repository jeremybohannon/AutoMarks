<template>
  <div class="loading-wrapper">
    <i class="fa fa-spinner rotating" aria-hidden="true"></i>Loading...
  </div>
</template>

<script>
export default {
  name: 'Loading'
}
</script>

<style scoped>
.loading-wrapper {
    height: 600px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

    font-size: 1.8em;
    color: grey;
    font-weight: 100;
    font-family: helvetica;
}

.fa-spinner {
  margin-right: 15px;
}

@keyframes rotating {
    from{
        transform: rotate(0deg);
    }
    to{
        transform: rotate(360deg);
    }
}

.rotating {
    animation: rotating 2s linear infinite;
}
</style>