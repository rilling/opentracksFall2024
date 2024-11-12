package de.dennisguse.opentracks.viewmodels;

import android.util.Pair;
import android.view.LayoutInflater;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.data.models.HeartRateZones;
import de.dennisguse.opentracks.databinding.StatsSensorItemBinding;
import de.dennisguse.opentracks.sensors.sensorData.SensorDataSet;
import de.dennisguse.opentracks.services.RecordingData;
import de.dennisguse.opentracks.settings.PreferencesUtils;
import de.dennisguse.opentracks.settings.UnitSystem;
import de.dennisguse.opentracks.ui.customRecordingLayout.DataField;
import de.dennisguse.opentracks.util.StringUtils;

public abstract class SensorStatisticsViewHolder extends StatisticViewHolder<StatsSensorItemBinding> {

    @Override
    protected StatsSensorItemBinding createViewBinding(LayoutInflater inflater) {
        return StatsSensorItemBinding.inflate(inflater);
    }

    @Override
    public void configureUI(DataField dataField) {
        getBinding().statsValue.setTextAppearance(dataField.isPrimary() ? R.style.TextAppearance_OpenTracks_PrimaryValue : R.style.TextAppearance_OpenTracks_SecondaryValue);
        getBinding().statsDescriptionMain.setTextAppearance(dataField.isPrimary() ? R.style.TextAppearance_OpenTracks_PrimaryHeader : R.style.TextAppearance_OpenTracks_SecondaryHeader);
    }

    public static class SensorHeartRate extends SensorStatisticsViewHolder {

        @Override
        public void onChanged(UnitSystem unitSystem, RecordingData data) {
            SensorDataSet sensorDataSet = data.sensorDataSet();
            String sensorName = getContext().getString(R.string.value_unknown);

            Pair<String, String> valueAndUnit;
            if (sensorDataSet != null && sensorDataSet.getHeartRate() != null) {
                valueAndUnit = StringUtils.getHeartRateParts(getContext(), sensorDataSet.getHeartRate().first);
                sensorName = sensorDataSet.getHeartRate().second;
            } else {
                valueAndUnit = StringUtils.getHeartRateParts(getContext(), null);
            }

            //TODO Loads preference every time
            HeartRateZones zones = PreferencesUtils.getHeartRateZones();
            int textColor;
            if (sensorDataSet != null && sensorDataSet.getHeartRate() != null) {
                textColor = zones.getTextColorForZone(getContext(), sensorDataSet.getHeartRate().first);
            } else {
                textColor = zones.getTextColorForZone(getContext(), null);
            }

            getBinding().statsValue.setText(valueAndUnit.first);
            getBinding().statsUnit.setText(valueAndUnit.second);
            getBinding().statsDescriptionMain.setText(R.string.stats_sensors_heart_rate);

            getBinding().statsDescriptionSecondary.setText(sensorName);

            getBinding().statsValue.setTextColor(textColor);
        }
    }

    public static abstract class SensorStatisticsViewHolderBase extends SensorStatisticsViewHolder {

        @Override
        public void onChanged(UnitSystem unitSystem, RecordingData data) {
            SensorDataSet sensorDataSet = data.sensorDataSet();
            String sensorName = getContext().getString(R.string.value_unknown);

            Pair<String, String> valueAndUnit = getValueAndUnit(sensorDataSet);
            sensorName = getSensorName(sensorDataSet, sensorName);

            updateUI(valueAndUnit, sensorName);
        }

        protected abstract Pair<String, String> getValueAndUnit(SensorDataSet sensorDataSet);
        protected abstract String getSensorName(SensorDataSet sensorDataSet, String defaultSensorName);
        protected abstract int getDescriptionText();

        private void updateUI(Pair<String, String> valueAndUnit, String sensorName) {
            getBinding().statsValue.setText(valueAndUnit.first);
            getBinding().statsUnit.setText(valueAndUnit.second);
            getBinding().statsDescriptionMain.setText(getDescriptionText());
            getBinding().statsDescriptionSecondary.setText(sensorName);
        }
    }

    public static class SensorCadence extends SensorStatisticsViewHolderBase {

        @Override
        protected Pair<String, String> getValueAndUnit(SensorDataSet sensorDataSet) {
            if (sensorDataSet != null && sensorDataSet.getCadence() != null) {
                return StringUtils.getCadenceParts(getContext(), sensorDataSet.getCadence().first);
            } else {
                return StringUtils.getCadenceParts(getContext(), null);
            }
        }

        @Override
        protected String getSensorName(SensorDataSet sensorDataSet, String defaultSensorName) {
            return sensorDataSet != null && sensorDataSet.getCadence() != null ? sensorDataSet.getCadence().second : defaultSensorName;
        }

        @Override
        protected int getDescriptionText() {
            return R.string.stats_sensors_cadence;
        }
    }

    public static class SensorPower extends SensorStatisticsViewHolderBase {

        @Override
        protected Pair<String, String> getValueAndUnit(SensorDataSet sensorDataSet) {
            if (sensorDataSet != null && sensorDataSet.getCyclingPower() != null) {
                return StringUtils.getPowerParts(getContext(), sensorDataSet.getCyclingPower().getValue());
            } else {
                return StringUtils.getCadenceParts(getContext(), null); 
            }
        }

        @Override
        protected String getSensorName(SensorDataSet sensorDataSet, String defaultSensorName) {
            return sensorDataSet != null && sensorDataSet.getCyclingPower() != null ?
                    sensorDataSet.getCyclingPower().getSensorNameOrAddress() : defaultSensorName;
        }

        @Override
        protected int getDescriptionText() {
            return R.string.stats_sensors_power;
        }
    }

}
