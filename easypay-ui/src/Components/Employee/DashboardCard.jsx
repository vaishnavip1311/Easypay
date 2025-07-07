function DashboardCard({ title, value, icon }) {
  return (
    <div className="col-12 md:col-6 lg:col-4">
      <div className="p-card p-3 shadow-2 surface-card border-round">
        <div className="flex align-items-center">
          <i className={`${icon} text-2xl text-primary mr-3`}></i>
          <div>
            <div className="text-sm text-500">{title}</div>
            <div className="text-xl font-bold text-900">{value}</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DashboardCard;
